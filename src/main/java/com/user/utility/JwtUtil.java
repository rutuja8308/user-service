package com.user.utility;

import java.security.Key;
import java.util.Date;
import java.util.List;

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

	public String generateToken(UserDetails userDetails)
	{
	    Date now = new Date();

	    List<String> roles = userDetails.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .toList();

	    return Jwts.builder()
	            .subject(userDetails.getUsername())
	            .claim("roles", roles)   // ✅ FIXED (plural + list)
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