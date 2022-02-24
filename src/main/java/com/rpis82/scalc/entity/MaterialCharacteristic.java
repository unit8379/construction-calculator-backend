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
@Table(name="material_characteristics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialCharacteristic {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="measurement_unit_id")
	private MeasurementUnit measurementUnit;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="material_id")
	private Material material;
	
	@Column(name="name")
	private String name;
	
	@Column(name="length")
	private double length;
	
	@Column(name="width")
	private double width;
	
	@Column(name="thickness")
	private double thickness;
	
	@Column(name="volume")
	private double volume;
}
