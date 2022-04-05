package com.rpis82.scalc.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="structural_element_frames")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StructuralElementFrame
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    // bidirectional связь с таблицей "openings_in_a_structural_element_frame"
    @OneToMany(mappedBy="structuralElementFrameId", cascade={CascadeType.ALL})
    private List<OpeningInAStructuralElementFrame> openings;
    
//    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name ="result_id")
//    private Result resultId;

    @Column(name="amount_floor")
    private int amountFloor;

    @Column(name="floor_number")
    private int floorNumber;

    @Column(name="floor_height")
    private int floorHeight;

    @Column(name="perimeter_of_external_walls")
    private double perimeterOfExternalWalls;

    @Column(name="base_area")
    private double baseArea;

    @Column(name="external_wall_thickness")
    private double externalWallThickness;

    @Column(name="internal_wall_length")
    private double internalWallLength;

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
    private double overlapThickness;

    @Column(name="OSB_overlap")
    private String osbOverlap;

    @Column(name="steam_waterproofing_overlap")
    private String steamWaterproofingOverlap;

    @Column(name="windscreen_overlap")
    private String windscreenOverlap;

    @Column(name="insulation_overlap")
    private String insulationOverlap;

    @Column(name="OSB_internal_wall")
    private String osbInternalWall;
}

