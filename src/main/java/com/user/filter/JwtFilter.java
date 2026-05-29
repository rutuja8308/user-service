package com.user.filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.user.utility.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService service;

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