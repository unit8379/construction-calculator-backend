package com.rpis82.scalc.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.Calculation;
import com.rpis82.scalc.entity.CalculationState;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculationDto {
	private int id;
	private CalculationState calculationState;
	private int number;
	private LocalDateTime createdDate;
	private String constructionObjectAddress;
	
	public Calculation toCalculation() {
		Calculation calculation = new Calculation();
		calculation.setId(id);
		calculation.setCalculationState(calculationState);
		calculation.setNumber(number);
		calculation.setCreatedDate(createdDate);
		calculation.setConstructionObjectAddress(constructionObjectAddress);
		
		return calculation;
	}
	
	public static CalculationDto fromCalculation(Calculation calculation) {
		CalculationDto calculationDto = new CalculationDto();
		calculationDto.setId(calculation.getId());
		calculationDto.setCalculationState(calculation.getCalculationState());
		calculationDto.setNumber(calculation.getNumber());
		calculationDto.setCreatedDate(calculation.getCreatedDate());
		calculationDto.setConstructionObjectAddress(calculation.getConstructionObjectAddress());
		
		return calculationDto;
	}
}
