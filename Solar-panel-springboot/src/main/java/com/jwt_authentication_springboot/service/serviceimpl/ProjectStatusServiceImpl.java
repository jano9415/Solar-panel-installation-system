package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.ProjectStatus;
import com.jwt_authentication_springboot.repository.ProjectStatusRepository;
import com.jwt_authentication_springboot.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Override
    public void save(ProjectStatus projectStatus) {
        projectStatusRepository.save(projectStatus);

    }

    @Override
    public ResponseEntity<ProjectStatus> findById(long id) {
        return ResponseEntity.ok(projectStatusRepository.findById(id).get());
    }

    //Keresés projekt változásának a dátuma szerint
    @Override
    public ProjectStatus findByStatusChanged(String statusChanged) {
        return projectStatusRepository.findByStatusChanged(statusChanged);
    }
}
