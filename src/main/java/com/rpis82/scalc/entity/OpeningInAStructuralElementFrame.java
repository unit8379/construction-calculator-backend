package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "openings_in_a_structural_element_frame")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningInAStructuralElementFrame
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name ="structural_element_frame_id")
    private StructuralElementFrame structuralElementFrameId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="opening_id")
    private Opening openingId;

    @Column(name="amount")
    private int amount;
}
