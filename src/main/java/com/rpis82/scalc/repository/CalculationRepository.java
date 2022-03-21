package com.rpis82.scalc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Customer;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Integer> {
	List<Calculation> findByCustomer(Customer customer);
}
