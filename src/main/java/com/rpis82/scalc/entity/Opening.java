package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="openings")
@Data
@AllArgsConstructor
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

    public Opening(){

    }

    public Opening(String type, double width, double height){
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString(){
        return String.format("User [id=%s, stateId=%s, firstName=%s, lastName=%s, secondName=%s, phone=%s, email=%s, login=%s, password=%s]",
                id, type, width, height);
    }
}
