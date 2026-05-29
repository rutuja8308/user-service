package com.user.utility;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private final String SECRET =
			"mysecretkeymysecretkeymysecretkey123456";

	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(UserDetails userDetails) {

		String role = userDetails.getAuthorities()
				.stream()
				.findFirst()
				.get()
				.getAuthority();

		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(
						new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractUsername(String token) {

		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) key)
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