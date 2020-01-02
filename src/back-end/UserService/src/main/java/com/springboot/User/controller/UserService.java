package com.springboot.User.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.User.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserService() {}
	
	public List<User> getUsers(){
		List<User> list = new ArrayList<User>();
		this.userRepository.findAll().forEach(list::add);
		return list;
	}
	
	public User addUser(User user) {
		return this.userRepository.save(user);
	}

	public Optional<User> getUser(String id) {
		return this.userRepository.findById(Integer.valueOf(id));
	}
	
	public Optional<User> getUserByLogin(String login) {
		return this.userRepository.findByLogin(login);
	}
	
}
