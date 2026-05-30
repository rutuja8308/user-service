package com.user.utility;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secret;

	private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

	public String generateToken(UserDetails userDetails) {

		String role = userDetails.getAuthorities()
		        .stream()
		        .map(GrantedAuthority::getAuthority)
		        .findFirst()
		        .orElseThrow(() -> new RuntimeException("No role assigned to user"));

		Date now = new Date();
		return Jwts.builder()
		        .subject(userDetails.getUsername())
		        .claim("role", role)
		        .issuedAt(now)
		        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
		        .signWith(getSigningKey())
		        .compact();
	}

	public String extractUsername(String token) {

		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public boolean validateToken(String token,
			UserDetails userDetails) {

		String username = extractUsername(token);

		return username.equals(userDetails.getUsername());
	}
}