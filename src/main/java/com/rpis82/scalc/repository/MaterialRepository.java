package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpis82.scalc.entity.Material;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer>{
}
