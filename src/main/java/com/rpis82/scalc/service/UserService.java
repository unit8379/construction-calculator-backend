package com.rpis82.scalc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User create(User user) {
		return userRepository.save(user);
	}
}
