package com.nt.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${app.key}")
private String secret;
	
/* this method use to Generate Token */
	public String generateToken(String subject) {
		return Jwts.builder()
				.setId("JWT")
				.setSubject(subject)
				.setIssuer("TCSIT")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(20)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();
				
		
	}//gtoken()
	
	/* This method use to read the claims form token */
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}//claim()
	
	/* This Method Read username from token */
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}//getu()
	
	/* This Method get the Expiration date from token */
	public Date getEpirationDate(String token) {
		return getClaims(token).getExpiration();
	}//getExpDate()
	
	/* This Method read curernt and 
	 * Expiration date from token 
	 *  and return boolean value
 */
	public boolean isTokenExpired(String token) {
      final Date expDate=getEpirationDate(token);
      return expDate.before(new Date(System.currentTimeMillis()));
	}//isValid()
	
	/* This Method read token user and 
	 *  requested user current and  also
	 * Expiration date from token 
	 *  and return boolean value
    */
	public boolean validateToken(String token,String username) {
		//read user form generated  token 
	   String tokenUname=getUsername(token);
	   //To Validation and return
	   return (tokenUname.contains(username)&& !isTokenExpired(token));
	}//isValidUser()

	
	
	
	
	
}//class
