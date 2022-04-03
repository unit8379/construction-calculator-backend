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
@Table(name="materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="measurement_unit_id")
	private MeasurementUnit measurementUnit;
    
    @Column(name="name")
    private String name;

    @Column(name="material_type")
    private String materialType;
    
    @Column(name="price")
    private double price;

    @Column(name="structural_element_type")
    private String structuralElementType;
}
