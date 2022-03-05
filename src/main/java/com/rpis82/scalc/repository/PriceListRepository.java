package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rpis82.scalc.entity.PriceList;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer>{
}
