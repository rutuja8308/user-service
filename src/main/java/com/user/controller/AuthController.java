package com.user.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AuthRequest;
import com.user.service.CustomUserDetailsService;
import com.user.utility.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager manager;

	private JwtUtil jwtUtil;

	private CustomUserDetailsService service;

	public AuthController(AuthenticationManager manager, JwtUtil jwtUtil, CustomUserDetailsService service) {
		super();
		this.manager = manager;
		this.jwtUtil = jwtUtil;
		this.service = service;
	}

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