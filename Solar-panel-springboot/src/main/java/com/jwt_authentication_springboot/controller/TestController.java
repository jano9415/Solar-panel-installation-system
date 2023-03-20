package com.jwt_authentication_springboot.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	// Publikus tartalom amit mindenki láthat.
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	// Csak akkor érheti el a kliens, ha van érvényes jwt tokenje.
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	// Csak akkor érheti el a kliens, ha van érvényes jwt tokenje és moderátor
	// szerepkörrel rendelkezik.
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	// Csak akkor érheti el a kliens, ha van érvényes jwt tokenje és admin
	// szerepkörrel rendelkezik.
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
