package com.rpis82.scalc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.repository.UserRepository;
import com.rpis82.scalc.repository.UserStateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final UserStateRepository userStateRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, UserStateRepository userStateRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userStateRepository = userStateRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User register(User userToSave) {
		// По умолчанию все созданные пользователи получают статус "1. Числится в штате".
		// Логика присвоения статуса "2. Уволен" пока что не предусмотрена.
		userToSave.setUserState(userStateRepository.findById(1).get());
		userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));  // шифрование пароля
		
		User registeredUser = userRepository.save(userToSave);
		log.info("IN register - user: {} successfully registred", registeredUser);
		
		return registeredUser;
	}
	
	public User findById(int userId) {
		User result = userRepository.findById(userId).orElse(null);
		
		if (result == null) {
			log.warn("IN findById - no user found by id: {}", userId);
			return null;
		}
		
		log.info("IN findById - user: {} found by id: {}", result, userId);
		return result;
	}
	
	public User findByLogin(String login) {
		User result = userRepository.findByLogin(login);
		log.info("IN findByLogin - user: {} found by username: {}", result, login);
		return result;
	}
	
	public List<User> getAll() {
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found", result.size());
		return result;
	}
	
	public void delete(int userId) {
		userRepository.deleteById(userId);
		log.info("IN delete - user with id: {} successfully deleted");
	}
}
