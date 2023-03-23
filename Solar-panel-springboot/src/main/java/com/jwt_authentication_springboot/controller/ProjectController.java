package com.jwt_authentication_springboot.controller;

import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.service.serviceimpl.ProjectPartServiceImpl;
import com.jwt_authentication_springboot.service.serviceimpl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private ProjectPartServiceImpl projectPartService;

    @PostMapping("/createproject")
    public void createProject(@RequestBody Project project){
        projectService.save(project);
    }

    @GetMapping("/findall")
    public List<Project> findAll(){

        return projectService.findAll();
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id){

        return projectService.findById(id);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Project project){
        projectService.update(id, project);
    }

    @GetMapping("/reservepart/{projectId}/{partId}/{reservedNumber}")
    public void reservePart(@PathVariable Long projectId, @PathVariable Long partId, @PathVariable int reservedNumber){
        projectService.reservePart(projectId,partId,reservedNumber);

    }

    @GetMapping("/prereservepart/{projectId}/{partId}/{preReservedNumber}")
    public void preReservePart(@PathVariable Long projectId, @PathVariable Long partId, @PathVariable int preReservedNumber){
        projectService.preReservePart(projectId,partId,preReservedNumber);

    }



}
