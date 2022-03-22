package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{ //pass in managed class and pk type

	Role findByName(String name); //finds user role by name
}
