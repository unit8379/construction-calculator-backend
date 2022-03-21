package com.rpis82.scalc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.User;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String secondName;
    private String phone;
    private String email;

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

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setSecondName(user.getSecondName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
