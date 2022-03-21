package com.rpis82.scalc.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.entity.UserState;

// Фабрика для создания JwtUser экземпляра из User экземпляра.
public final class JwtUserFactory {
	
	public JwtUserFactory() {}
	
	public static JwtUser create(User user) {
		return new JwtUser(
					user.getId(),
					user.getLogin(),
					user.getPassword(),
					user.getFirstName(),
					user.getLastName(),
					user.getSecondName(),
					user.getPhone(),
					user.getEmail(),
					true,
					new Date(),
					mapStatusToGrantedAuthorities(user.getUserState())
				);
	}
	
	private static List<GrantedAuthority> mapStatusToGrantedAuthorities(UserState userState) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userState.getName()));
		
		return authorities;
	}
}
