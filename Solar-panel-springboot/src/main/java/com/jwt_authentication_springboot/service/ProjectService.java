package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    public void save(Project project);

    public ResponseEntity<Project> findById(long id);

    List<Project> findAll();

    void update(Long id, Project project);

    void reservePart(Long projectId, Long partId, int reservedNumber);

    void preReservePart(Long projectId, Long partId, int preReservedNumber);
}
