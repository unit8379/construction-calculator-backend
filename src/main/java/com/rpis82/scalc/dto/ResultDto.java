package com.rpis82.scalc.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class ResultDto {
	private String type;
	private String material;
	private String measurementUnit;
	private double amount;
	private BigDecimal totalCost;
}
