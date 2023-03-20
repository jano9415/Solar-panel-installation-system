package com.jwt_authentication_springboot.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt_authentication_springboot.model.ERole;
import com.jwt_authentication_springboot.model.Role;
import com.jwt_authentication_springboot.model.User;
import com.jwt_authentication_springboot.payload.request.LoginRequest;
import com.jwt_authentication_springboot.payload.request.SignUpRequest;
import com.jwt_authentication_springboot.payload.response.JwtResponse;
import com.jwt_authentication_springboot.payload.response.MessageResponse;
import com.jwt_authentication_springboot.repository.RoleRepository;
import com.jwt_authentication_springboot.repository.UserRepositroy;
import com.jwt_authentication_springboot.security.jwt.JwtUtils;
import com.jwt_authentication_springboot.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepositroy userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	// Bejelentkezés
	// A bejövő paraméterek a klienstől: felhasználói név, jelszó.
	// A visszatérési érték egy JwtResponse objektum ami tartalmazza: jwt token, id,
	// felhasználói név, email cím.
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Jwt token generálása
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	// Regisztráció
	// Felhasználói név és email cím ellenőrzése, hogy létezik-e már az adatbázisban

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Új felhasználó létrehozása
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		// Felhasználó szerepköreinek beállítása
		if (strRoles == null) {
			Role expertRole = roleRepository.findByName(ERole.admin)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(expertRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "storeleader":
					Role storeleaderRole = roleRepository.findByName(ERole.storeleader)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(storeleaderRole);

					break;
				case "storeemployee":
					Role storeemployeeRole = roleRepository.findByName(ERole.storeemployee)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(storeemployeeRole);

					break;
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.admin)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
				default:
					Role expertRole = roleRepository.findByName(ERole.expert)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(expertRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
