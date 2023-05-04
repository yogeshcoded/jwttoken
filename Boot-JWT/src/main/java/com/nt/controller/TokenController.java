package com.nt.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.UserInfo;
import com.nt.model.UserRequest;
import com.nt.model.UserResponse;
import com.nt.service.IUserService;
import com.nt.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class TokenController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil util;
	@Autowired
	private IUserService service;

	@PostMapping("/save")
	public ResponseEntity<String> registerUser(@RequestBody UserInfo info) {
		String msg = service.saveUserInfo(info);
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@GetMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		 String token = util.generateToken(request.getUsername());
		 return new ResponseEntity<>(new UserResponse(token, "Token generated"), HttpStatus.OK);
	
	}

	@GetMapping("/home")
	public ResponseEntity<String> homePage(Principal p) {
		String message = "Welcome " + p.getName();
		return new ResponseEntity<>(message, HttpStatus.OK);

	}
}
