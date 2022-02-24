package com.rpis82.scalc.entity;

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
	@JoinColumn("material_characteristic_id")
	private MaterialCharacteristic materialCharacteristic;
	
	@Column(name="material")
	private String material;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="measurement_unit")
	private String measurementUnit;
	
	@Column(name="price")
	private double price;
	
	@Column(name="full_price")
	private double fullPrice;
}
