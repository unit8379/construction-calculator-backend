package com.rpis82.scalc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.Customer;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
	private int id;
    private String firstName;
    private String lastName;
    private String secondName;
    private String phone;
    private String email;
    private String address;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setSecondName(secondName);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);

        return customer;
    }

    public static CustomerDto fromCustomer(Customer customer) {
    	CustomerDto customerDto = new CustomerDto();
    	customerDto.setId(customer.getId());
    	customerDto.setFirstName(customer.getFirstName());
    	customerDto.setLastName(customer.getLastName());
    	customerDto.setSecondName(customer.getSecondName());
    	customerDto.setPhone(customer.getPhone());
    	customerDto.setEmail(customer.getEmail());
    	customerDto.setAddress(customer.getAddress());

        return customerDto;
    }
}
