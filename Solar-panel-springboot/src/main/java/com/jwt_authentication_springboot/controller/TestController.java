package com.jwt_authentication_springboot.controller;


import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.model.ProjectPart;
import com.jwt_authentication_springboot.service.serviceimpl.ProjectPartServiceImpl;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class Proba{
		public int id;
		public Long partId;
		public Long projectId;

	}

	@Autowired
	private ProjectPartServiceImpl projectPartService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/findall")
	public Proba findAll(){

		Proba proba = new Proba();


		List<ProjectPart> projectParts = projectPartService.findAll();
		proba.setId(projectParts.get(0).getId());
		proba.setPartId(projectParts.get(0).getPart().getId());
		proba.setProjectId(projectParts.get(0).getProject().getId());

		return proba;


	}

	@GetMapping("/findbyid/{id}")
	public ProjectPart findById(@PathVariable Long id){
		System.out.println(projectPartService.findById(id).getPart());
		return projectPartService.findById(id);
	}

	@GetMapping("/find")
	public ProjectPart find(){
		return projectPartService.findById(35L);
	}

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
