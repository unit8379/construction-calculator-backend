package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rpis82.scalc.entity.Opening;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningRepository extends JpaRepository<Opening, Integer> {
}
