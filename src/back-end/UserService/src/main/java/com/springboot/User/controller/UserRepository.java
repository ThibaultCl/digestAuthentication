package com.springboot.User.controller;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.User.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByLogin(String login);

}
