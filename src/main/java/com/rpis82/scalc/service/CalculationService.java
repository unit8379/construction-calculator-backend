package com.rpis82.scalc.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpis82.scalc.dto.ResultsDto;
import com.rpis82.scalc.dto.StructuralElementFrameDto;
import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.entity.Material;
import com.rpis82.scalc.entity.Opening;
import com.rpis82.scalc.entity.OpeningInAStructuralElementFrame;
import com.rpis82.scalc.entity.Result;
import com.rpis82.scalc.entity.StructuralElementFrame;
import com.rpis82.scalc.repository.CalculationRepository;
import com.rpis82.scalc.repository.CalculationStateRepository;
import com.rpis82.scalc.repository.MaterialRepository;
import com.rpis82.scalc.repository.OpeningInAStructuralElementFrameRepository;
import com.rpis82.scalc.repository.ResultRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CalculationService {
	
	@Autowired
	private CalculationRepository calculationRepository;
	
	@Autowired
	private CalculationStateRepository calculationStateRepository;
	
	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private OpeningInAStructuralElementFrameRepository openingInASEFrameRepository;
	
	public List<Calculation> getAll(Customer customer) {
		List<Calculation> result = calculationRepository.findByCustomer(customer);
		
		return result;
	}
	
	public Calculation getOne(int calculationId) {
		return calculationRepository.getById(calculationId);
	}
	
	public Calculation changeState(int calculationId) {
		Calculation calculation = calculationRepository.getById(calculationId);
		
		if (calculation.getCalculationState().getId() == 1) {
			calculation.setCalculationState(calculationStateRepository.getById(2));
		} else if (calculation.getCalculationState().getId() == 2) {
			calculation.setCalculationState(calculationStateRepository.getById(1));
		}
		
		return calculation;
	}
	
	public Calculation create(Calculation calculationToCreate) {
		
		// по умолчанию статус "Не актуален"
		calculationToCreate.setCalculationState(calculationStateRepository.getById(2));
		return calculationRepository.save(calculationToCreate);
	}
	
	public Calculation copy(int calculationId) {
		Calculation calcToCopy = calculationRepository.getById(calculationId);
		List<Result> resultsToCopy = resultRepository.findAllByCalculation(calcToCopy);
		List<StructuralElementFrame> framesToCopy = new ArrayList<>();
		
		int amountFloor = resultsToCopy.get(0).getStructuralElementFrame().getAmountFloor();
		for (int i = 0, j = 0; i != amountFloor; j++) {
			if (resultsToCopy.get(j).getStructuralElementFrame().getFloorNumber() > i) {
				framesToCopy.add(resultsToCopy.get(j).getStructuralElementFrame());
				i++;
			}
		}
		
		List<StructuralElementFrameDto> dtos = new ArrayList<>();
		for (int i = 0; i < amountFloor; ++i) {
			StructuralElementFrameDto dto = new StructuralElementFrameDto();
			List<Opening> openings = new ArrayList<>();
			List<Integer> amounts = new ArrayList<>();
			
			for (OpeningInAStructuralElementFrame op : openingInASEFrameRepository.findAllByStructuralElementFrameId(framesToCopy.get(i))) {
				Opening opening = new Opening();
				opening.setType(op.getOpeningId().getType());
				opening.setWidth(op.getOpeningId().getWidth());
				opening.setHeight(op.getOpeningId().getHeight());
				
				openings.add(opening);
				amounts.add(op.getAmount());
			}
			
			dto.setOpenings(openings);
			dto.setAmounts(amounts);
			dto.setAmountFloor(framesToCopy.get(i).getAmountFloor());
			dto.setFloorNumber(framesToCopy.get(i).getFloorNumber());
			dto.setFloorHeight(framesToCopy.get(i).getFloorHeight());
			dto.setPerimeterOfExternalWalls(framesToCopy.get(i).getPerimeterOfExternalWalls());
			dto.setBaseArea(framesToCopy.get(i).getBaseArea());
			dto.setExternalWallThickness(framesToCopy.get(i).getExternalWallThickness());
			dto.setInternalWallLength(framesToCopy.get(i).getInternalWallLength());
			dto.setInternalWallThickness(framesToCopy.get(i).getInternalWallThickness());
			dto.setOsbExternalWall(framesToCopy.get(i).getOsbExternalWall());
			dto.setSteamWaterproofingExternalWall(framesToCopy.get(i).getSteamWaterproofingExternalWall());
			dto.setWindscreenExternalWall(framesToCopy.get(i).getWindscreenExternalWall());
			dto.setInsulationExternalWall(framesToCopy.get(i).getInsulationExternalWall());
			dto.setOsbInternalWall(framesToCopy.get(i).getOsbInternalWall());
			dto.setOverlapThickness(framesToCopy.get(i).getOverlapThickness());
			dto.setOsbOverlap(framesToCopy.get(i).getOsbOverlap());
			dto.setSteamWaterproofingOverlap(framesToCopy.get(i).getSteamWaterproofingOverlap());
			dto.setWindscreenOverlap(framesToCopy.get(i).getWindscreenOverlap());
			dto.setInsulationOverlap(framesToCopy.get(i).getInsulationOverlap());
			
			dtos.add(dto);
		}
		
		Calculation copiedCalculation = new Calculation();
		copiedCalculation.setConstructionObjectAddress(calcToCopy.getConstructionObjectAddress());
		copiedCalculation.setCreatedDate(calcToCopy.getCreatedDate());
		copiedCalculation.setCustomer(calcToCopy.getCustomer());
		copiedCalculation.setNumber(calcToCopy.getNumber());
		copiedCalculation.setCalculationState(calculationStateRepository.getById(2));
		calculationRepository.save(copiedCalculation);
		
		addSEFrame(copiedCalculation.getId(), dtos);
		
		return copiedCalculation;
	}
	
	public void delete(int calculationId) {
		Calculation calculationToDelete = calculationRepository.getById(calculationId);
		resultRepository.deleteAllByCalculation(calculationToDelete);
		calculationRepository.deleteById(calculationId);
	}
	
	public void update(int calculationId, Calculation updatedInfo) {
		Calculation calculationToUpdate = calculationRepository.getById(calculationId);
		calculationToUpdate.setConstructionObjectAddress(updatedInfo.getConstructionObjectAddress());
		calculationToUpdate.setCreatedDate(updatedInfo.getCreatedDate());
		calculationToUpdate.setNumber(updatedInfo.getNumber());
	}
	
	public List<ResultsDto> getResults(int calculationId) {
		// получение списка Result для данного расчёта
		Calculation calculation = calculationRepository.getById(calculationId);
		List<Result> results = resultRepository.findAllByCalculation(calculation);
		
		// упаковка результатов в список удобных дто объектов
		List<ResultsDto> dtos = new ArrayList();
		for (int i = 0; i < results.get(0).getStructuralElementFrame().getAmountFloor(); ++i) {
			ResultsDto dto = new ResultsDto();
			dto.toResultsDto(results, i + 1);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	public List<ResultsDto> addSEFrame(Integer calculationId, List<StructuralElementFrameDto> SEFrameDtos) {
		// Удаление прошлых результатов по данному расчёту, если они были.
		// Таким образом, данный метод работает как для добавления так и для обновления даннных по каркасу в расчёте.
		Calculation calculationToDelete = calculationRepository.getById(calculationId);
		resultRepository.deleteAllByCalculation(calculationToDelete);
		
		List<StructuralElementFrame> framesToProcess = new ArrayList();
		
		for (StructuralElementFrameDto dto : SEFrameDtos) {
			StructuralElementFrame frame = dto.toSEFrame();
			
			log.warn("IN addSEFrame - proccessing frame: {}", frame);
			
			// каждому проёму устанавливаем ссылку на структурный элемент "Каркас"
			for (OpeningInAStructuralElementFrame op : dto.toOpeningsInASEFrame()) {
				op.setStructuralElementFrameId(frame);
				
				// Проёмы, сохраняясь в БД, каскадно сохранят и каркас
				openingInASEFrameRepository.save(op);
			}
			
			framesToProcess.add(frame);
		}
		
		// формирование и сохранение результатов для всех этажей
		List<Result> formedResutls = resultRepository.saveAll(resultsGeneration(calculationId, framesToProcess));
		
		// упаковка этажей в список удобных дто объектов
		List<ResultsDto> dtos = new ArrayList();
		for (int i = 0; i < SEFrameDtos.size(); ++i) {
			ResultsDto dto = new ResultsDto();
			dto.toResultsDto(formedResutls, i + 1);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	private List<Result> resultsGeneration(Integer calculationId, List<StructuralElementFrame> framesToProcess) {
		List<Result> resultsToSave = new ArrayList();
		
		for (StructuralElementFrame frame : framesToProcess) {
			// ОБЩИЕ ДАННЫЕ
			// Площадь внешних стен - D13 * D11 * D7
			double externalWallsArea = frame.getPerimeterOfExternalWalls() * frame.getFloorHeight() * frame.getAmountFloor();
			// Площадь внутренних стен - D19 * D11 * D7
			double internalWallsArea = frame.getInternalWallLength() * frame.getFloorHeight() * frame.getAmountFloor();
			
			// ВНЕШНИЕ СТЕНЫ
			// Кол-во досок на внешние стойки - ceil( D13 / 0.6 + 1 )
			double numberOfBoardsForExternalRacks = Math.ceil(frame.getPerimeterOfExternalWalls() / 0.6 + 1);
			// Кол-во досок для основания - D13 * 2 / 3
			double basementBoardsNumber = frame.getPerimeterOfExternalWalls() * 2 / 3;
			// Кол-досок на проёмы - ceil ( ((B33 + D33) * 2 * F33 + (B39 + D39) * F39) / 3 )
			double windowHeight = 0, windowWidth = 0, windowAmount = 0;
			double externalDoorHeight = 0, externalDoorWidth = 0, externalDoorAmount = 0;
			for (OpeningInAStructuralElementFrame op : openingInASEFrameRepository.findAllByStructuralElementFrameId(frame)) {
				if (op.getOpeningId().getType().equals("Оконные проёмы")) {
					windowHeight = op.getOpeningId().getHeight();
					windowWidth = op.getOpeningId().getWidth();
					windowAmount = op.getAmount();
				} else if (op.getOpeningId().getType().equals("Дверные проёмы внешние")) {
					externalDoorHeight = op.getOpeningId().getHeight();
					externalDoorWidth = op.getOpeningId().getWidth();
					externalDoorAmount = op.getAmount();
				}
			}
			double openingsBoardsNumber = Math.ceil(((windowHeight + windowWidth) * 2 * windowAmount + (externalDoorHeight + externalDoorWidth) * externalDoorAmount) / 3);
			// Итого кол-во досок на внешние стены
			double externalWallsBoardsNumber = numberOfBoardsForExternalRacks + basementBoardsNumber + openingsBoardsNumber;
			// Ширина доски на внешние стены - D17
			double externalWallBoardWidth = frame.getExternalWallThickness();
			// Объём досок на внешние стены
			double externalWallBoardsVolume = externalWallsBoardsNumber * (externalWallBoardWidth / 1000) * 0.05 * 3;
			// Площадь ОСБ
			double areaOSB = externalWallsArea * 2 * 1.15;
			// Площадь парогидроизоляции
			double steamWaterproofingArea = externalWallsArea * 1.15;
			// Площадь ветрозащиты
			double windscreenArea = externalWallsArea * 1.15;
			// Площадь утеплителя на внешние стены
			double externalWallsInsulationArea = externalWallsArea * 1.1 - (windowHeight * windowWidth * windowAmount) - (externalDoorHeight * externalDoorWidth * externalDoorAmount);
			// Толщина утеплителя - D17
			double insulationThickness = frame.getExternalWallThickness();
			// Объём утеплителя
			double insulationVolume = externalWallsInsulationArea * insulationThickness / 1000;
			
			// ВНУТРЕННИЕ СТЕНЫ
			// Кол-во досок на внутренние стойки - ceil( D19 / 0.6 )
			double numberOfBoardsForInternalRacks = Math.ceil(frame.getInternalWallLength() / 0.6);
			// Кол-досок на проёмы - ceil ( (B45 + D45) * 2 * F45 / 3 )
			double internalDoorHeight = 0, internalDoorWidth = 0, internalDoorAmount = 0;
			for (OpeningInAStructuralElementFrame op : openingInASEFrameRepository.findAllByStructuralElementFrameId(frame)) {
				if (op.getOpeningId().getType().equals("Дверные проёмы внутренние")) {
					internalDoorHeight = op.getOpeningId().getHeight();
					internalDoorWidth = op.getOpeningId().getWidth();
					internalDoorAmount = op.getAmount();
				}
			}
			double internalOpeningsBoardsNumber = Math.ceil((internalDoorHeight * internalDoorWidth) * 2 * internalDoorAmount / 3);
			// Итого кол-во досок на внутренние стены
			double internalWallsBoardsNumber = numberOfBoardsForInternalRacks + internalOpeningsBoardsNumber;
			// Ширина доски на внутернние стойки - D21
			double internalRacksBoardWidth = frame.getInternalWallThickness();
			// Объём досок на внутренние стойки
			double internalRacksBoardsVolume = internalWallsBoardsNumber * internalRacksBoardWidth / 1000 * 3 * 0.05;
			// Площадь ОСБ
			double internalOSBArea = internalWallsArea * 2 * 1.15;
			
			// ПЕРЕКРЫТИЯ
			// Кол-во балок перекрытий - ceil( D15 * 0.7 )
			double overlapBeamsNumber = Math.ceil(frame.getBaseArea() * 0.7);
			// Ширина доски на балки перекрытия - D64
			double boardForBeamWidth = frame.getOverlapThickness();
			// Объём досок на перекрытия
			double overlapBoardsVolume = overlapBeamsNumber * 0.05 * boardForBeamWidth / 1000 * 6;
			// Площадь ОСБ - D15 * 1.15 * 2 * 2
			double overlapOSBArea = frame.getBaseArea() * 1.15 * 2 * 2;
			if (frame.getFloorNumber() != 1) overlapOSBArea /= 2;
			// Площадь парогидроизоляции - D15 * 1.15
			double overlapSteamWaterproofingArea = frame.getBaseArea() * 1.15;
			// Площадь ветрозащиты
			double overlapWindscreenArea = frame.getBaseArea() * 1.15;
			// Площадь утеплителя перекрытия
			double overlapInsulationArea = frame.getBaseArea() * 1.1 * 2;
			if (frame.getFloorNumber() != 1) overlapInsulationArea /= 2;
			// Толщина утеплителя - D64
			double overlapInsulationThickness = frame.getOverlapThickness();
			// Объём утеплителя
			double overlapInsulationVolume = overlapInsulationArea * overlapInsulationThickness / 1000;
			
			// Внешние стены, добавление результатов
			Result res = new Result();
			
			// Доска
			if (Double.compare(frame.getExternalWallThickness(), 100.0) == 0) {
				Material mat = materialRepository.getById(1);
				res.setMaterial(mat);
				res.setAmount(externalWallBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * externalWallBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getExternalWallThickness(), 150.0) == 0) {
				Material mat = materialRepository.getById(2);
				res.setMaterial(mat);
				res.setAmount(externalWallBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * externalWallBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getExternalWallThickness(), 200.0) == 0) {
				Material mat = materialRepository.getById(3);
				res.setMaterial(mat);
				res.setAmount(externalWallBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * externalWallBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getExternalWallThickness(), 250.0) == 0) {
				Material mat = materialRepository.getById(4);
				res.setMaterial(mat);
				res.setAmount(externalWallBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * externalWallBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			resultsToSave.add(res);
			
			// Утеплитель
			Material mat = materialRepository.findByName(frame.getInsulationExternalWall());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(insulationVolume);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * insulationVolume, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// ОСБ
			mat = materialRepository.findByName(frame.getOsbExternalWall());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(areaOSB);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * areaOSB, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Парогидроизоляция
			mat = materialRepository.findByName(frame.getSteamWaterproofingExternalWall());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(steamWaterproofingArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * steamWaterproofingArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Ветрозащита
			mat = materialRepository.findByName(frame.getWindscreenExternalWall());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(windscreenArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * windscreenArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Внутренние стены, добавление результатов
			// Доска
			res = new Result();
			if (Double.compare(frame.getInternalWallThickness(), 100.0) == 0) {
				mat = materialRepository.getById(1);
				res.setMaterial(mat);
				res.setAmount(internalRacksBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * internalRacksBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getInternalWallThickness(), 150.0) == 0) {
				mat = materialRepository.getById(2);
				res.setMaterial(mat);
				res.setAmount(internalRacksBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * internalRacksBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getInternalWallThickness(), 200.0) == 0) {
				mat = materialRepository.getById(3);
				res.setMaterial(mat);
				res.setAmount(internalRacksBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * internalRacksBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getInternalWallThickness(), 250.0) == 0) {
				mat = materialRepository.getById(4);
				res.setMaterial(mat);
				res.setAmount(internalRacksBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * internalRacksBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			resultsToSave.add(res);
			
			// ОСБ
			mat = materialRepository.findByName(frame.getOsbInternalWall());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(internalOSBArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * internalOSBArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Перекрытия, добавление результатов
			// Доска
			res = new Result();
			if (Double.compare(frame.getOverlapThickness(), 200.0) == 0) {
				mat = materialRepository.getById(8);
				res.setMaterial(mat);
				res.setAmount(overlapBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * overlapBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			else if (Double.compare(frame.getOverlapThickness(), 250.0) == 0) {
				mat = materialRepository.getById(9);
				res.setMaterial(mat);
				res.setAmount(overlapBoardsVolume);
				res.setStructuralElementFrame(frame);
				res.setCalculation(calculationRepository.getById(calculationId));
				res.setFullPrice(new BigDecimal(mat.getPrice() * overlapBoardsVolume, new MathContext(2, RoundingMode.DOWN)));
			}
			resultsToSave.add(res);
			
			// Утеплитель
			mat = materialRepository.findByName(frame.getInsulationOverlap());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(overlapInsulationVolume);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * overlapInsulationVolume, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// ОСБ
			mat = materialRepository.findByName(frame.getOsbOverlap());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(overlapOSBArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * overlapOSBArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Парогидроизоляция
			mat = materialRepository.findByName(frame.getSteamWaterproofingOverlap());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(overlapSteamWaterproofingArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * overlapSteamWaterproofingArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
			
			// Ветрозащита
			mat = materialRepository.findByName(frame.getWindscreenOverlap());
			res = new Result();
			res.setMaterial(mat);
			res.setAmount(overlapWindscreenArea);
			res.setStructuralElementFrame(frame);
			res.setCalculation(calculationRepository.getById(calculationId));
			res.setFullPrice(new BigDecimal(mat.getPrice() * overlapWindscreenArea, new MathContext(2, RoundingMode.DOWN)));
			resultsToSave.add(res);
		}
		
		return resultsToSave;
	}
}






