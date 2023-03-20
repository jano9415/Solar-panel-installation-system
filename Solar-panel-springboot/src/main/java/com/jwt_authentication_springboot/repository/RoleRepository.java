package com.jwt_authentication_springboot.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jwt_authentication_springboot.model.ERole;
import com.jwt_authentication_springboot.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	// Keresés név szerint
	Optional<Role> findByName(ERole name);

}
