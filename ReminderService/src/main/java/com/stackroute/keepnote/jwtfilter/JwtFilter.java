package com.stackroute.keepnote.jwtfilter;


import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;



/* This class implements the custom filter by extending org.springframework.web.filter.GenericFilterBean.  
 * Override the doFilter method with ServletRequest, ServletResponse and FilterChain.
 * This is used to authorize the API access for the application.
 */


public class JwtFilter extends GenericFilterBean {

	
	
	

	/*
	 * Override the doFilter method of GenericFilterBean.
     * Retrieve the "authorization" header from the HttpServletRequest object.
     * Retrieve the "Bearer" token from "authorization" header.
     * If authorization header is invalid, throw Exception with message. 
     * Parse the JWT token and get claims from the token using the secret key
     * Set the request attribute with the retrieved claims
     * Call FilterChain object's doFilter() method */
	
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

       
		final HttpServletRequest request2 = (HttpServletRequest) request;
		final String authHeader = request2.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			throw new ServletException("Invalid header");
		}
		final String jwsToken = authHeader.substring(7);
		JwtParser jwtParcer = Jwts.parser().setSigningKey("secretKey");
		Jwt jwt = jwtParcer.parse(jwsToken);
		Claims claims = (Claims) jwt.getBody();
		request2.setAttribute("claims", claims);
		String userId = claims.getSubject();
		HttpSession session = request2.getSession();
		session.setAttribute("LoggedInUserId", userId);
		chain.doFilter(request2, response);
	
    }
}
