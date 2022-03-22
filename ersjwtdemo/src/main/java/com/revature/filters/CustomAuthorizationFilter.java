package com.revature.filters;



import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/login")) {
			
			filterChain.doFilter(request, response); //this just passes the request to the next filter in the filter chain
		
		} else {
			String authHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
			
			if(authHeader != null && authHeader.startsWith("Bearer ")) {
				
				try {
					String token = authHeader.substring("Bearer ".length()); //remove String "Bearer " and only get token
					
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //algorithm to sign JWT and refresh token
					
					JWTVerifier verifier = JWT.require(algorithm).build();//verify token
					
					DecodedJWT decodedJWT = verifier.verify(token);
					
					String username = decodedJWT.getSubject(); //gets username that comes with token
					
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class); //grabs value of key "roles" from JWT token 
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(); 
					
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role)); //this loops through roles and converts them to something that extends authority in the application
					});
					
					UsernamePasswordAuthenticationToken authToken 
					= new UsernamePasswordAuthenticationToken(username, null, authorities); 
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					filterChain.doFilter(request, response);
				}catch(Exception e) {
					log.error("Error logging in: {}", e.getMessage());
					
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
//					response.sendError(HttpStatus.FORBIDDEN.value()); //returns status code
					
					Map<String, String> error = new HashMap<>(); 
					error.put("error_message: ", e.getMessage()); 
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
				 
			} else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
