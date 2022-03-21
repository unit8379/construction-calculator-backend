package com.rpis82.scalc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.security.jwt.JwtUser;
import com.rpis82.scalc.security.jwt.JwtUserFactory;
import com.rpis82.scalc.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByLogin(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User with login: " + username + " not found");
		}
		
		JwtUser jwtUser = JwtUserFactory.create(user);
		log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
		return jwtUser;
	}

}
