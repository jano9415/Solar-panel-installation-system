package com.jwt_authentication_springboot.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jwt_authentication_springboot.model.User;

public interface UserRepositroy extends CrudRepository<User, Long> {

	// Keresés felhasználói név szerint
	Optional<User> findByUsername(String username);

	// Létezik az alábbi felhasználói név az adatbázisban?
	Boolean existsByUsername(String username);

	// Létezik az alábbi email cím az adatbázisban?
	Boolean existsByEmail(String email);

}
