package com.revature.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.revature.filters.CustomAuthenticationFilter;
import com.revature.filters.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration //Spring
@EnableWebSecurity
@RequiredArgsConstructor //Dependency injection
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //Allows us to override certain spring security methods
	private final UserDetailsService userDetailsService; //injects userDetailsService into constructor created by adding the @RequiredArgsConstructor annotation 
	private final BCryptPasswordEncoder bCryptPasswordEncoder; //best type as of 2020
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); //Bean that tells Spring where to go look for users
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  
		
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("ROLE_FINANCE_MANAGER");
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_FINANCE_MANAGER");
		http.authorizeHttpRequests().anyRequest().authenticated(); 
		http.addFilter(new CustomAuthenticationFilter(authManagerBean())); 
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	} 
	
	@Bean
	public AuthenticationManager authManagerBean() throws Exception {
		return super.authenticationManagerBean(); 
	}
	
	

}
