package com.rpis82.scalc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpis82.scalc.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByLogin(String login);
}
