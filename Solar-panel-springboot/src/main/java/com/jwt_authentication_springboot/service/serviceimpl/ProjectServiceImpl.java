package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.repository.ProjectRepository;
import com.jwt_authentication_springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void save(Project project) {
        projectRepository.save(project);

    }

    @Override
    public ResponseEntity<Project> findById(long id) {
        return ResponseEntity.ok(projectRepository.findById(id).get());
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
