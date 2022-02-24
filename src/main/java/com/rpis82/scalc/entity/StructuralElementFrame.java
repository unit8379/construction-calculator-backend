package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name ="structural_element_frames")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StructuralElementFrame
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name ="result_id")
    private Result resultId;

    @Column(name="amount_floor")
    private int amountFloor;

    @Column(name="floor_number")
    private int floor_number;

    @Column(name="floor_height")
    private int floorHeight;

    @Column(name="perimeter_of_external_walls")
    private double perimeterOfExternalWalls;

    @Column(name="base_Area")
    private double baseArea;

    @Column(name="external_wall_thidness")
    private int externalWallThidness;

    @Column(name="internal_wall_lenght")
    private int internalWallLenght;

    @Column(name="internal_wall_thickness")
    private double internalWallThickness;

    @Column(name="OSB_external_wall")
    private String osbExternalWall;

    @Column(name="steam_waterproofing_external_wall")
    private String steamWaterproofingExternalWall;

    @Column(name="windscreen_external_wall")
    private String windscreenExternalWall;

    @Column(name="insulation_external_wall")
    private String insulationExternalWall;

    @Column(name="overlap_thickness")
    private String overlapThickness;

    @Column(name="OSB_thickness")
    private String osbThickness;

    @Column(name="steam_waterproofing_thickness")
    private String steamWaterproofingThickness;

    @Column(name="windscreen_thickness")
    private String windscreenThickness;

    @Column(name="insulation_thickness")
    private String insulationThickness;

    @Column(name="OSB_internal_wall")
    private String osbInternalWall;
}

