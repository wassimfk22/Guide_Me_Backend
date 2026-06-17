package com.guide.me.guider.Security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
	
	private final Key SECRET_KEY = Keys.secretKeyFor( SignatureAlgorithm.HS256 );
	private final long JWT_EXPIRATION = 1000 * 60 * 60 *10;
	
	private String createToken ( Map <String, Object> claims, String username ) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt( new Date (System.currentTimeMillis()) )
				.setExpiration( new Date (System.currentTimeMillis() +JWT_EXPIRATION) )
				.signWith(SECRET_KEY)
				.compact();
	}
	
	public String generateToken ( String username ) {
		Map <String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}
	
	private Claims extractAllClaims ( String token ) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T extractClaim ( String token, Function <Claims, T> extracter ) {
		Claims claims = extractAllClaims(token);
		return extracter.apply(claims);
	}
	
	public Date extractExpirationDate ( String token ) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUsername ( String token ) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isTokenExpired ( String token ) {
		return extractExpirationDate(token).before(new Date());
	}
	
	public boolean validateToken ( String token, String username ) {
		return ( !isTokenExpired(token) && extractUsername(token).equals(username) );
	}
	

}
