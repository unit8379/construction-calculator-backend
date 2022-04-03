package com.rpis82.scalc.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="calculation_id")
	private Calculation calculation;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="basement_structural_element_id")
	private BasementStructuralElement basementStructuralElement;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="structural_element_frame_id")
	private StructuralElementFrame structuralElementFrame;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="material_id")
	private Material material;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="full_price")
	private BigDecimal fullPrice;
}
