package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name="name")
    private String name;

    @Column(name="material_type")
    private String materialType;

    @Column(name="structural_element_type")
    private String structuralElementType;
}
