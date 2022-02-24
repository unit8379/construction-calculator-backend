package com.rpis82.scalc.entity;

import java.time.LocalDateTime;

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
@Table(name="price_lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="material_characteristic_id")
	private MaterialCharacteristic materialCharacteristic;
	
	@Column(name="date")
	private LocalDateTime date;
	
	@Column(name="purchase_price")
	private double purchasePrice;
	
	@Column(name="selling_price")
	private double sellingPrice;
}
