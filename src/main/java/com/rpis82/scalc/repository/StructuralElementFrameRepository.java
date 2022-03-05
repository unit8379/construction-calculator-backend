package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpis82.scalc.entity.StructuralElementFrame;
import org.springframework.stereotype.Repository;

@Repository
public interface StructuralElementFrameRepository extends JpaRepository<StructuralElementFrame, Integer>{
}
