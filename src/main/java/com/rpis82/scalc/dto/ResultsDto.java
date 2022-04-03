package com.rpis82.scalc.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.Result;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class ResultsDto {
	
	private List<ResultDto> externalWalls;
	
	private List<ResultDto> internalWalls;
	
	private List<ResultDto> overlaps;
	
	private BigDecimal totalCost;
	
	// Запаковка списка результатов в дто
	public void toResultsDto(List<Result> resultsInput, int floor) {
		externalWalls = new ArrayList();
		internalWalls = new ArrayList();
		overlaps = new ArrayList();
		totalCost = new BigDecimal(0, new MathContext(2, RoundingMode.DOWN));
		
		List<Result> results = new ArrayList();
		
		for (int i = 0; i < resultsInput.size(); ++i) {
			if (resultsInput.get(i).getStructuralElementFrame().getFloorNumber() == floor) {
				results.add(resultsInput.get(i));
			}
		}
		
		for (int i = 0; i < 5; ++i) {
			ResultDto res = new ResultDto();
			res.setType(results.get(i).getMaterial().getMaterialType());
			res.setMaterial(results.get(i).getMaterial().getName());
			res.setMeasurementUnit(results.get(i).getMaterial().getMeasurementUnit().getName());
			res.setAmount(results.get(i).getAmount());
			res.setTotalCost(results.get(i).getFullPrice());
			
			totalCost = totalCost.add(results.get(i).getFullPrice());
			externalWalls.add(res);
		}
		
		for (int i = 5; i < 7; ++i) {
			ResultDto res = new ResultDto();
			res.setType(results.get(i).getMaterial().getMaterialType());
			res.setMaterial(results.get(i).getMaterial().getName());
			res.setMeasurementUnit(results.get(i).getMaterial().getMeasurementUnit().getName());
			res.setAmount(results.get(i).getAmount());
			res.setTotalCost(results.get(i).getFullPrice());
			
			totalCost = totalCost.add(results.get(i).getFullPrice());
			internalWalls.add(res);
		}
		
		for (int i = 7; i < 12; ++i) {
			ResultDto res = new ResultDto();
			res.setType(results.get(i).getMaterial().getMaterialType());
			res.setMaterial(results.get(i).getMaterial().getName());
			res.setMeasurementUnit(results.get(i).getMaterial().getMeasurementUnit().getName());
			res.setAmount(results.get(i).getAmount());
			res.setTotalCost(results.get(i).getFullPrice());
			
			totalCost = totalCost.add(results.get(i).getFullPrice());
			overlaps.add(res);
		}
	}
}
