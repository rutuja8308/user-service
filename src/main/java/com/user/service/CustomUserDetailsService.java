package com.user.service;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService 
 {
	private UserRepository repo;
	
	public CustomUserDetailsService(UserRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

	    User user = repo.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    System.out.println("USER = " + user.getUsername());
	    System.out.println("ROLES FROM DB = " + user.getRoles());
	    
	    return new org.springframework.security.core.userdetails.User(
	            user.getUsername(),
	            user.getPassword(),
	            user.getRoles()
	                    .stream()
	                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
	                    .collect(Collectors.toSet())
	    );
	}
}
