package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpis82.scalc.entity.OpeningInAStructuralElementFrame;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningInAStructuralElementFrameRepository extends JpaRepository<OpeningInAStructuralElementFrame, Integer>{
}
