package com.revature.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data //Lombok
@NoArgsConstructor @AllArgsConstructor 
public class AppUser {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //pk
	private String name; 
	private String username; 
	private String password; 
	
	@ManyToMany(fetch = FetchType.EAGER)//load the roles when we load user
	private Collection<Role> roles = new ArrayList<>(); 

}
