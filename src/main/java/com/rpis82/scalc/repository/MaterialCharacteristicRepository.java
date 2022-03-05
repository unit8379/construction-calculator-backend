package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpis82.scalc.entity.MaterialCharacteristic;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCharacteristicRepository extends JpaRepository<MaterialRepository, Integer>{
}
