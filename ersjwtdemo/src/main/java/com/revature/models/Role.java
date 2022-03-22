package com.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Data //Lombok
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Role {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //pk
	private String name; //name of the role

}
