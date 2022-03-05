package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.BasementStructuralElement;

@Repository
public interface BasementStructuralElementRepository extends JpaRepository<BasementStructuralElement, Integer> {

}
