package com.springboot.gateway.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springboot.User.model.User;

@Service
public class GatewayService {
	
	@Value("${uri.userService}")
	String userServiceUri;
	
	@Value("${digest.realm}")
	String digestRealm;

	/**
	 * Add a new User to the UserService
	 * @param User model from UserService
	 * @return the User added
	 * @throws NoSuchAlgorithmException 
	 */
	public User addUser(User user){
		// Encode the user password
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String pwd = user.getLogin() + ":" + digestRealm + ":" + user.getPassword();
			user.setPassword(new String(Hex.encode(md.digest(pwd.getBytes()))));
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <User> request = new HttpEntity<User>(user, headers);
		String url = userServiceUri + "/user";

		// Send request
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, request, User.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			// Successful request
			return response.getBody();
		}
		else {
			// Request failed
			return response.getBody();
		}
	}

	/**
	 * Find a User by its id
	 * @param id of the User
	 * @return a User from the UserService
	 */
	public User getUser(String id) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <User> request = new HttpEntity<User>(headers);
		String url = userServiceUri + "/user/" + id;
		
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, request,User.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			// Successful request
			if (response.getBody() == null) {
				// User not found
				return response.getBody();
			}
			else {
				//User found
				return response.getBody();
			}
		}
		else {
			// Request failed
			return response.getBody();
		}
	}

}
