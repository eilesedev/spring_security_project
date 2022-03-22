package com.revature.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repositories.RoleRepo;
import com.revature.repositories.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional 
@Slf4j //Log4j
public class UserServiceImpl implements UserService, UserDetailsService{

	private final UserRepo userRepo; 
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username); 
		if(user == null) {
			log.error("User not found in the database!");
			throw new UsernameNotFoundException("User not found in the database!"); 
		} else {
			log.info("User found in the database");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();//define authorities for below
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}); //get roles from user found in the database
		return new User(user.getUsername(), user.getPassword(), authorities); //This is Spring's authenticated user: for password comparison and checking authorities
	}
	
	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("Saving new user {} to the db", user.getName());
		return userRepo.save(user); //call userRepo and pass in user to save to db
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the db", role.getName());
		return roleRepo.save(role); //call roleRepo and pass in role to save to db
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to user {}", roleName, username);
		AppUser user = userRepo.findByUsername(username); 
		Role role = roleRepo.findByName(roleName); 
	
		user.getRoles().add(role); //Lombok must be installed in IDE to access this method
		
	}

	@Override
	public AppUser getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsername(username); //find user by username
	}

	@Override
	public List<AppUser> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

	@Override
	public List<Role> getRoles() {
		return roleRepo.findAll();
	}

}
