package com.rpis82.scalc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpis82.scalc.dto.CalculationDto;
import com.rpis82.scalc.dto.ResultsDto;
import com.rpis82.scalc.dto.StructuralElementFrameDto;
import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.service.CalculationService;
import com.rpis82.scalc.service.CustomerService;

@RestController
@RequestMapping(value = "/calculations")
public class CalculationController {
	
	@Autowired
	private CalculationService calculationService;
	
	@Autowired
	private CustomerService customerService;
	
	// получить все расчёты для указанного клиента
	@GetMapping(value = "/{customerId}")
	public List<CalculationDto> getAll(@PathVariable(name="customerId") int customerId) {

		// Выбранный клиент находится по переданному в пути id.
		Customer curCustomer = customerService.findById(customerId);
		
		// Из сервиса получаем список всех связанных с данным клиентом расчётов,
		// а затем составляем из него список DTO расчётов для передачи на фронт.
		List<CalculationDto> toResponse = new ArrayList<>();
		
		for (Calculation el : calculationService.getAll(curCustomer)) {
			toResponse.add(CalculationDto.fromCalculation(el));
		}
		
		return toResponse;
	}
	
	// получить один расчёт по его id
	@GetMapping(value = "/one/{calculationId}")
	public CalculationDto getOne(@PathVariable(name="calculationId") int calculationId) {
		CalculationDto calcToReturn = CalculationDto.fromCalculation(calculationService.getOne(calculationId));
		return calcToReturn;
	}
	
	// Изменить статус расчёта на противоположный
	@PutMapping(value = "/state/{calculationId}")
	public CalculationDto changeCalculationState(@PathVariable(name="calculationId") int calculationId) {
		return CalculationDto.fromCalculation(calculationService.changeState(calculationId));
	}
	
	// создать новый пустой расчёт
	@PostMapping(value = "/create/{customerId}")
	public CalculationDto create(@PathVariable(name="customerId") int customerId,
			@RequestBody Calculation calculationToCreate) {
		
		// Выбранный клиент находится по переданному в пути id.
		Customer curCustomer = customerService.findById(customerId);
		calculationToCreate.setCustomer(curCustomer);
		
		// дополнительное поле stateName заполняем, чтобы объект нормально сериализовался с именем состояния
		CalculationDto toReturn = CalculationDto.fromCalculation(calculationService.create(calculationToCreate));
		toReturn.setStateName(toReturn.getCalculationState().getName());
		
		return toReturn;
	}
	
	// обновить расчёт
	@PostMapping(value = "/update/{calculationId}")
	public void update(@PathVariable(name="calculationId") int calculationId,
			@RequestBody Calculation updatedInfo) {
			
		calculationService.update(calculationId, updatedInfo);
	}
	
	// скопировать расчёт
	@GetMapping(value = "/copy/{calculationId}")
	public CalculationDto copy(@PathVariable(name="calculationId") int calculationId) {
		
		return CalculationDto.fromCalculation(calculationService.copy(calculationId));
	}
	
	// удалить всё, что связано с указанным расчётом и его самого
	@DeleteMapping(value = "/delete/{calculationId}")
	public void delete(@PathVariable(name="calculationId") int calculationId) {
		calculationService.delete(calculationId);
	}
	
	// получение списка результатов по конкретному расчёту
	@GetMapping(value = {"/results/{calculationId}"})
	public List<ResultsDto> getResults(@PathVariable(name="calculationId") int calculationId) {
		return calculationService.getResults(calculationId);
	}
	
	// добавить в расчёт результаты связанные с переданной информацией
	// по структурному элементу "Каркас"
	@PostMapping(value = {"/seframe/{calculationId}"})
	public List<ResultsDto> addSEFrameIntoCalculation(
			@PathVariable(name="calculationId", required=false)
			Integer calculationId,
			@RequestBody
			List<StructuralElementFrameDto> SEFrameDtos)
	{
		return calculationService.addSEFrame(calculationId, SEFrameDtos);
	}
	
}
