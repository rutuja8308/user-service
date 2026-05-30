package com.user.config;

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

	private JwtFilter jwtFilter;
	
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_MANAGER = "MANAGER";
	
	public SecurityConfig(JwtFilter jwtFilter) {
		super();
		this.jwtFilter = jwtFilter;
	}

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
						.hasRole(ROLE_ADMIN)

						.requestMatchers("/users/delete/**")
						.hasRole(ROLE_ADMIN)

						.requestMatchers("/users/update/**")
						.hasAnyRole(ROLE_ADMIN, ROLE_MANAGER)

						.requestMatchers("/users/search/**")
						.hasAnyRole(ROLE_ADMIN, ROLE_USER, ROLE_MANAGER)

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