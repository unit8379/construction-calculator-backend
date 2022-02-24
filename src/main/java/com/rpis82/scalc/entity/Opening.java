package com.rpis82.scalc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="openings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opening {

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="opening_id")
    private Opening_in_a_structural_element_frame opening_in_a_structural_element_frame;
}
