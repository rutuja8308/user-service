package com.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.user.filter.JwtFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration config)
			throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {

		http.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/auth/**").permitAll()

						.requestMatchers("/users/save").permitAll()

						.requestMatchers("/users/findAll")
						.hasRole("ADMIN")

						.requestMatchers("/users/delete/**")
						.hasRole("ADMIN")

						.requestMatchers("/users/update/**")
						.hasAnyRole("ADMIN", "MANAGER")

						.requestMatchers("/users/search/**")
						.hasAnyRole("ADMIN", "USER", "MANAGER")

						.requestMatchers("/users/getFromProp")
						.authenticated()

						.anyRequest()
						.authenticated())

				.sessionManagement(session -> session
						.sessionCreationPolicy(
								SessionCreationPolicy.STATELESS))

				.addFilterBefore(
						jwtFilter,
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}