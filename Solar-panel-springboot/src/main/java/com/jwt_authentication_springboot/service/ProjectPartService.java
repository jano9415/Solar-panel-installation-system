package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.ProjectPart;

import java.util.List;

public interface ProjectPartService {

    void save(ProjectPart projectPart);

    List<ProjectPart> findAll();

    ProjectPart findById(Long id);

    ProjectPart findByProjectIdAndPartId(Long projectId, Long partId);

    void deleteProjectPart(Long projectPartId);
}
