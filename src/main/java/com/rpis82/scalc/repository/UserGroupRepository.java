package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.UserGroup;

@Repository
// Таблица без главного ключа не подхватывается как JPA репозиторий (возможно стоит избавиться от него)
//public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
//	
//}
public interface UserGroupRepository {
	
}