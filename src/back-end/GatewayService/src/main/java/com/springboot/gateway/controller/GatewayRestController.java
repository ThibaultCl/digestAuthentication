package com.springboot.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.User.model.User;


@RestController
public class GatewayRestController {
	
	@Autowired
	private GatewayService gatewayService;

	@RequestMapping("/")
	private String root() {
		return "Welcome to the Gateway service !";
	}
	
	@RequestMapping(value = "/secure")
    public String secure() {
        return "You are authorize to access this page. This is secure page. ";
    }
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	private User addUser(@RequestBody User user) {
		return this.gatewayService.addUser(user);
	}
	
	@RequestMapping("/user/{id}")
	private User getUser(@PathVariable String id){
		return gatewayService.getUser(id);
	}
	
}
