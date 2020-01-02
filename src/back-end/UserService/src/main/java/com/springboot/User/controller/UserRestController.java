package com.springboot.User.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.User.model.User;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	private String root() {
		return "Welcome to the User service !";
	}
	
	@RequestMapping("/users")
	private List<User> getUsers(){
		return this.userService.getUsers();
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	private User addUser(@RequestBody User user) {
		return this.userService.addUser(user);
	}
	
	@RequestMapping("/user/{id}")
	private User getUser(@PathVariable String id){
		Optional<User> user = this.userService.getUser(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	@RequestMapping("/user/auth/{login}")
	private User getUserByLogin(@PathVariable String login) {
		Optional<User> user = this.userService.getUserByLogin(login);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
}
