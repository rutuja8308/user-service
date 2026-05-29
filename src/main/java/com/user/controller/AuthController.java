package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.user.dto.AuthRequest;
import com.user.service.CustomUserDetailsService;
import com.user.utility.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService service;

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {

		manager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()));

		UserDetails userDetails =
				service.loadUserByUsername(request.getUsername());

		return jwtUtil.generateToken(userDetails);
	}
}