package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="openings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opening {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="type")
    private String type;

    @Column(name="width")
    private double width;

    @Column(name="height")
    private double height;


    @Override
    public String toString(){
        return String.format("Opening [id=%s, type=%s, width=%s, height=%s]",
                id, type, width, height);
    }
}
