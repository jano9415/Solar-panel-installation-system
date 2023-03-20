package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.ProjectStatus;
import org.springframework.http.ResponseEntity;

public interface ProjectStatusService {

    public void save(ProjectStatus projectStatus);

    public ResponseEntity<ProjectStatus> findById(long id);
}
