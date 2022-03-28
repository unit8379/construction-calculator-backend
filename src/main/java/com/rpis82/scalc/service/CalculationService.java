package com.rpis82.scalc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpis82.scalc.dto.StructuralElementFrameDto;
import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.entity.OpeningInAStructuralElementFrame;
import com.rpis82.scalc.entity.StructuralElementFrame;
import com.rpis82.scalc.repository.CalculationRepository;
import com.rpis82.scalc.repository.CalculationStateRepository;
import com.rpis82.scalc.repository.OpeningInAStructuralElementFrameRepository;
import com.rpis82.scalc.repository.ResultRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
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
	private OpeningInAStructuralElementFrameRepository openingInASEFrameRepository;
	
	public List<Calculation> getAll(Customer customer) {
		List<Calculation> result = calculationRepository.findByCustomer(customer);
		
		return result;
	}
	
	public Calculation create(Calculation calculationToCreate) {
		
		// пока что по умолчанию статус "Актуален"
		calculationToCreate.setCalculationState(calculationStateRepository.getById(1));
		return calculationRepository.save(calculationToCreate);
	}
	
	public void delete(int calculationId) {
		Calculation calculationToDelete = calculationRepository.getById(calculationId);
		resultRepository.deleteAllByCalculation(calculationToDelete);
	}
	
	public void addSEFrame(Integer calculationId, List<StructuralElementFrameDto> SEFrameDtos) {
		for (StructuralElementFrameDto dto : SEFrameDtos) {
			StructuralElementFrame frame = dto.toSEFrame();
			
			log.warn("IN addSEFrame - proccessing frame: {}", frame);
			
			// каждому проёму устанавливаем ссылку на структурный элемент "Каркас"
			for (OpeningInAStructuralElementFrame op : dto.toOpeningsInASEFrame()) {
				op.setStructuralElementFrameId(frame);
				
				// Проёмы, сохраняясь в БД, каскадно сохранят и каркас
				openingInASEFrameRepository.save(op);
			}
			
			// todo формирование результатов для указанного расчёта
			// todo список amounts добавить в json тело запроса
			// todo рефактор бд материалы
			
		}	
	}
	
	
}






