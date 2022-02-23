package com.rpis82.scalc.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="state_id")
	private UserState userState;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="second_name")
	private String secondName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="e_mail")
	private String email;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;

	public User() {
		
	}
	
	public User(String firstName, String lastName, String secondName, String phone, String email,
			String login, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.secondName = secondName;
		this.phone = phone;
		this.email = email;
		this.login = login;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState stateId) {
		this.userState = stateId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format(
				"User [id=%s, stateId=%s, firstName=%s, lastName=%s, secondName=%s, phone=%s, email=%s, login=%s, password=%s]",
				id, userState, firstName, lastName, secondName, phone, email, login, password);
	}
}
