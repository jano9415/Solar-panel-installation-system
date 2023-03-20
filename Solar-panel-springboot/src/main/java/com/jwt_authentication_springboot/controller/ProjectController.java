package com.jwt_authentication_springboot.controller;

import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.service.serviceimpl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @PostMapping("/createProject")
    public void createProject(@RequestBody Project project){
        projectService.save(project);
    }

    @GetMapping("/findall")
    public List<Project> findAll(){
        return projectService.findAll();
    }


}
