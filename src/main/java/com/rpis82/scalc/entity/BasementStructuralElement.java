package com.rpis82.scalc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="basement_structural_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasementStructuralElement {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
//	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//	@JoinColumn(name="result_id")
//	private Result result;
	
	@Column(name="perimeter_of_external_walls")
	private double perimeterOfExternalWalls;

	@Column(name="internal_walls_length")
	private double internalWallsLength;

	@Column(name="concrete_piles")
	private String concretePiles;

	@Column(name="concrete")
	private String concrete;
}
