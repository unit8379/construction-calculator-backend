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
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="calculation_id")
	private Calculation calculation;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="basement_structural_element_id")
	private BasementStructuralElement basementStructuralElement;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="structural_element_frame_id")
	private StructuralElementFrame structuralElementFrame;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="material_characteristic_id")
	private MaterialCharacteristic materialCharacteristic;
	
	@Column(name="material")
	private String material;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="measurement_unit")
	private String measurementUnit;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="full_price")
	private BigDecimal fullPrice;
}
