package com.nt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nt.util.JwtUtil;
@Component
public class SecurityFilter extends OncePerRequestFilter {
	@Autowired
private JwtUtil util;
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// read token form header
		String token=request.getHeader("Authorization");
		if(token!=null) {
			//read Username from token using Jwtutil method
			String username=util.getUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				//load the db user 
				UserDetails dbuser = userDetailsService.loadUserByUsername(username);
				//validate request user and db user same or not
				boolean isValid=util.validateToken(token, dbuser.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authToken=
							new UsernamePasswordAuthenticationToken(username,
                                                                                                           dbuser.getPassword(),
                                                                                                           dbuser.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//link authentication to security object
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}//if3
			}//if2
		}//if1
		filterChain.doFilter(request, response);
	}//method

}//class
