package com.rpis82.scalc.security.jwt;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Представление сущности User с точки зрения Spring Security
public class JwtUser implements UserDetails {

	private final int id;
	private final String username; // login
	private final String password;
	private final String firstName;
	private final String lastName;
	private final String secondName;
	private final String phone;
	private final String email;
	private final boolean enabled;	// активирован ли пользователь в системе (если нет, то он отключен и не имеет к ней доступа)
	private final Date lastPasswordResetDate;   // дата последнего сброса пароля
	private final Collection<? extends GrantedAuthority> authorities;  // выданные права
	
	public JwtUser(int id, String username, String password, String firstName, String lastName, String secondName,
			String phone, String email, boolean enabled, Date lastPasswordResetDate,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.secondName = secondName;
		this.phone = phone;
		this.email = email;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.authorities = authorities;
	}

	@JsonIgnore
	public int getId() {
		return id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
