package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableCaching
public class UserServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(UserServiceApplication.class, args);
	    
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "123456";
        String encodedPassword = encoder.encode(password);

        System.out.println("Encoded Password: " + encodedPassword);
		
	}
}
