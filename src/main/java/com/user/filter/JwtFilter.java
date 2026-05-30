package com.user.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.user.utility.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private JwtUtil jwtUtil;

	private UserDetailsService service;
	
	public JwtFilter(JwtUtil jwtUtil, UserDetailsService service) {
		super();
		this.jwtUtil = jwtUtil;
		this.service = service;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getServletPath();
		if (path.startsWith("/auth")) 
		{
			filterChain.doFilter(request, response); return; 
		}
		String authHeader = request.getHeader("Authorization"); 
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			token = authHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}

		if (username != null
				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails =
					service.loadUserByUsername(username);

			if (jwtUtil.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities());

				SecurityContextHolder.getContext()
						.setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}