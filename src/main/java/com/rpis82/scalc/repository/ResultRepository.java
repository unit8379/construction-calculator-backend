package com.rpis82.scalc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
	void deleteAllByCalculation(Calculation calculation);
	
	List<Result> findAllByCalculation(Calculation calculation);
}
