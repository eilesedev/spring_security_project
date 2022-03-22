package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.AppUser;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long>{ //pass in class to be managed and type of ID

	AppUser findByUsername(String username); 
}
