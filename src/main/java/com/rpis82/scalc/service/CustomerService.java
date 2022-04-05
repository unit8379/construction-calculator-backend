package com.rpis82.scalc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpis82.scalc.dto.CustomerDto;
import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.repository.CustomerRepository;
import com.rpis82.scalc.repository.UserRepository;
import com.rpis82.scalc.security.jwt.JwtUser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Customer create(Customer customerToCreate) {
		// При создании клиента, он получает себе текущего залогиненного менеджера (пользователя)
		JwtUser principal = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		customerToCreate.setUser(userRepository.findByLogin(principal.getUsername()));
		
		return customerRepository.save(customerToCreate);
	}
	
	public CustomerDto update(CustomerDto updatedInfo, int customerId) {
		Customer customerToUpdate = customerRepository.getById(customerId);
		customerToUpdate.setFirstName(updatedInfo.getFirstName());
		customerToUpdate.setLastName(updatedInfo.getLastName());
		customerToUpdate.setSecondName(updatedInfo.getSecondName());
		customerToUpdate.setAddress(updatedInfo.getAddress());
		customerToUpdate.setEmail(updatedInfo.getEmail());
		customerToUpdate.setPhone(updatedInfo.getPhone());
		
		return CustomerDto.fromCustomer(customerToUpdate);
	}
	
	public List<Customer> getAll() {
		// Достаём юзернейм залогиненного пользователя и ищем всех его клиентов
		JwtUser principal = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return customerRepository.findByUser(userRepository.findByLogin(principal.getUsername()));
	}
	
	public Customer findById(int customerId) {
		Customer result = customerRepository.findById(customerId).orElse(null);
		
		if (result == null) {
			log.warn("IN findById - no customer found by id: {}", customerId);
			return null;
		}
		
		log.info("IN findById - customer: {} found by id: {}", result, customerId);
		return result;
	}
}
