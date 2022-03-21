package com.rpis82.scalc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpis82.scalc.dto.CalculationDto;
import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.repository.CalculationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CalculationService {
	
	@Autowired
	private CalculationRepository calculationRepository;
	
	public List<Calculation> getAll(Customer customer) {
		List<Calculation> result = calculationRepository.findByCustomer(customer);
		
		return result;
	}
}
