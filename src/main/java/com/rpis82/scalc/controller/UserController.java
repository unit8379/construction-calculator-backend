package com.rpis82.scalc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpis82.scalc.entity.User;
import com.rpis82.scalc.entity.UserState;
import com.rpis82.scalc.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public User create(@RequestBody User userToSave) {
		userToSave.setUserState(new UserState("В штате"));
		return userService.create(userToSave);
	}
	
	@GetMapping
	public String hello() {
		return "it's working!";
	}
	
//  Это я проверял POST запросы с обычными параметрами. Пока что оставил в комментариях для общего развития.
//	@PostMapping
//	public User create(@RequestParam("fn") String fn, @RequestParam("ln") String ln, @RequestParam("sn") String sn,
//			@RequestParam("ph") String ph, @RequestParam("em") String em, @RequestParam("l") String l, @RequestParam("p") String p) {
//		System.out.println(fn);
//		return userService.create(new User(fn, ln, sn, ph, em, l, p));
//	}
}
