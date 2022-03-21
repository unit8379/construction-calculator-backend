package com.rpis82.scalc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpis82.scalc.dto.CalculationDto;
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
	
	@GetMapping(value = "{customerId}")
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
}
