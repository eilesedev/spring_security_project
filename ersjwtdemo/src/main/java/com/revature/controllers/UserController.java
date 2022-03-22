package com.revature.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.services.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController // makes class a controller
@RequestMapping("/api")
@RequiredArgsConstructor // to inject field into the constructor
public class UserController {

	private final UserService userService;

	// return a list of all users
	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers() { // call service and return users from db
		return ResponseEntity.ok().body(userService.getUsers());
	};

	// save a user
	@PostMapping("/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString()); //pass URI through header
		return ResponseEntity.created(uri).body(userService.saveUser(user)); //the .created() can be used to create a more specific http response than 200
	};

	// save a role
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString()); //pass URI through header	
		return ResponseEntity.created(uri).body(userService.saveRole(role)); //the .created() can be used to create a more specific http response than 200
	};
	
	// add a role to a user
		@PostMapping("/role/addToUser")
		public ResponseEntity<?> addRoleToUser(@RequestBody RoleDTO dto) {
			userService.addRoleToUser(dto.getUsername(), dto.getRoleName());
			return ResponseEntity.ok().build(); //no body just build response
		};
		
}

@Data //for getters and setters
class RoleDTO{
	private String username; 
	private String roleName; 
}
