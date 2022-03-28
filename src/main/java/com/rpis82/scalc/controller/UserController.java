package com.rpis82.scalc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping("/register")
	public User register(@RequestBody User userToSave) {
		return userService.register(userToSave);
	}
	
	@GetMapping
	public String hello() {
		return "it's working!";
	}
	
	// для примера. в самом приложении пока не нужен
	@GetMapping("/{userId}")
	public User getUser(@PathVariable int userId) {
		return userService.findById(userId);
	}
	
//  Это я проверял POST запросы с обычными параметрами. Пока что оставил в комментариях для общего развития.
//	@PostMapping
//	public User create(@RequestParam("fn") String fn, @RequestParam("ln") String ln, @RequestParam("sn") String sn,
//			@RequestParam("ph") String ph, @RequestParam("em") String em, @RequestParam("l") String l, @RequestParam("p") String p) {
//		System.out.println(fn);
//		return userService.create(new User(fn, ln, sn, ph, em, l, p));
//	}
}
