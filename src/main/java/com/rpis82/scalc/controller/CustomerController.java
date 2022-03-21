package com.rpis82.scalc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpis82.scalc.dto.CustomerDto;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public Customer create(@RequestBody Customer customerToSave) {
		return customerService.create(customerToSave);
	}
	
	@GetMapping
	public List<CustomerDto> getAll() {
		List<Customer> result = customerService.getAll();
		List<CustomerDto> toResponse = new ArrayList<>();
		
		for (Customer el : result) {
			toResponse.add(CustomerDto.fromCustomer(el));
		}
		
		return toResponse;
	}
}
