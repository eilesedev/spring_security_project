package com.revature.services;

import java.util.List;

import com.revature.models.AppUser;
import com.revature.models.Role;

//here we define methods for application to manage users
//this service gets injected into our controller to manage incoming users
public interface UserService {
	
	AppUser saveUser(AppUser user); //saves user in the db
	
	Role saveRole(Role role); //saves user role in the db
	
	void addRoleToUser(String username, String roleName);//attach role name to a user in db
	
	AppUser getUser(String username);
	
	List<AppUser>getUsers(); //return list of users
	
	List<Role>getRoles(); //return list of roles

}
