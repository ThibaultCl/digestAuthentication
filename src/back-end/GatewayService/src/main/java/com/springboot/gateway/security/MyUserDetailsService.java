package com.springboot.gateway.security;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Value("${uri.userService}")
	String uri;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Create request to the UserService to get User data
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> request = new HttpEntity<String>(headers);
		String url = uri + "/user/auth/" + username;
		
		// Send request
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			// Got response for the UserService
			String stringUser = response.getBody();
			//System.out.println("Response : " + stringUser);
			if (stringUser == null)
			{
				// Username not found
				throw new UsernameNotFoundException("Username not found by the UserService for the username : " + username);
			}
			else {
				JSONObject JSONUser = new JSONObject(stringUser);
				User user = new User(JSONUser.getString("login"), JSONUser.getString("password"), new ArrayList<>());
				return user;
			}
		}
		else {
			// Fail to reach UserService
			throw new UsernameNotFoundException("Request to the UserService failed, can't load user data. "
					+ "Request status code : " + Integer.toString(response.getStatusCodeValue()));
		}
	}

}
