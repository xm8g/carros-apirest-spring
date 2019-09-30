package com.carros.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carros.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByLogin(String login); 
}
