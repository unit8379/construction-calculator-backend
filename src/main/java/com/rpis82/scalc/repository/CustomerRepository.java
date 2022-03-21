package com.rpis82.scalc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.Customer;
import com.rpis82.scalc.entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	List<Customer> findByUser(User user);
}
