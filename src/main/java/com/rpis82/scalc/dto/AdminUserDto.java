package com.rpis82.scalc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.User;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
	private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String secondName;
    private String phone;
    private String email;
    private String state; // админская дто хранит ещё и статус пользователя

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSecondName(secondName);
        user.setPhone(phone);
        user.setEmail(email);

        return user;
    }

    public static AdminUserDto fromUser(User user) {
    	AdminUserDto adminUserDto = new AdminUserDto();
    	adminUserDto.setId(user.getId());
    	adminUserDto.setLogin(user.getLogin());
    	adminUserDto.setFirstName(user.getFirstName());
    	adminUserDto.setLastName(user.getLastName());
    	adminUserDto.setSecondName(user.getSecondName());
    	adminUserDto.setPhone(user.getPhone());
    	adminUserDto.setEmail(user.getEmail());
    	adminUserDto.setState(user.getUserState().getName());

        return adminUserDto;
    }
}
