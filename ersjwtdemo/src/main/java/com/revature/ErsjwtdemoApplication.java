package com.revature;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.services.UserService;

@SpringBootApplication
public class ErsjwtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErsjwtdemoApplication.class, args);
	}
	
	@Bean //this overrides spring implementation
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			//add roles
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_FINANCE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_EMPLOYEE"));
			
			//add users
			userService.saveUser(new AppUser(null, "Kidney Bean", "kidney", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Brussel Sprout", "bsprout", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Broccoli Stalk", "broccoli", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Green Asparagus", "ga_paragus", "1234", new ArrayList<>()));
			
			//add roles to users
			userService.addRoleToUser("kidney", "ROLE_USER");
			userService.addRoleToUser("kidney", "ROLE_EMPLOYEE");
			userService.addRoleToUser("broccoli", "ROLE_USER");
			userService.addRoleToUser("broccoli", "ROLE_EMPLOYEE");
			userService.addRoleToUser("bsprout", "ROLE_USER");
			userService.addRoleToUser("bsprout", "ROLE_FINANCE_MANAGER");
			userService.addRoleToUser("ga_paragus", "ROLE_USER");
			userService.addRoleToUser("ga_paragus", "ROLE_FINANCE_MANAGER");
			
		};
	}


}
