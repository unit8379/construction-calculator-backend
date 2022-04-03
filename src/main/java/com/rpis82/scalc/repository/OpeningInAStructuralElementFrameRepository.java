package com.rpis82.scalc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.OpeningInAStructuralElementFrame;
import com.rpis82.scalc.entity.StructuralElementFrame;

@Repository
public interface OpeningInAStructuralElementFrameRepository extends JpaRepository<OpeningInAStructuralElementFrame, Integer>{
	List<OpeningInAStructuralElementFrame> findAllByStructuralElementFrameId(StructuralElementFrame structuralElementFrame);
}
