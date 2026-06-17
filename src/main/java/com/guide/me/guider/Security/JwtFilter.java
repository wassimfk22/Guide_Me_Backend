package com.guide.me.guider.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private CustomUserDetailsService userDetails;
	private JwtUtil jwUtil;
	
	public JwtFilter ( CustomUserDetailsService userDetails, JwtUtil jwUtil ) {
		this.userDetails = userDetails;
		this.jwUtil = jwUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if ( authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) {
			jwt = authorizationHeader.substring(7);
			username = this.jwUtil.extractUsername(jwt);
		}
		if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			UserDetails user = this.userDetails.loadUserByUsername(username);
			if ( this.jwUtil.validateToken(jwt, user.getUsername()) ) {
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	

}
