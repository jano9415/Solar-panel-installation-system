package com.jwt_authentication_springboot.service.serviceimpl;


import com.jwt_authentication_springboot.model.ProjectPart;
import com.jwt_authentication_springboot.repository.ProjectPartRepository;
import com.jwt_authentication_springboot.service.ProjectPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPartServiceImpl implements ProjectPartService {

    @Autowired
    ProjectPartRepository projectPartRepository;


    @Override
    public void save(ProjectPart projectPart) {
        projectPartRepository.save(projectPart);
    }

    @Override
    public List<ProjectPart> findAll() {
        return projectPartRepository.findAll();



    }

    @Override
    public ProjectPart findById(Long id) {
        return projectPartRepository.findById(id).get();
    }

    @Override
    public ProjectPart findByProjectIdAndPartId(Long projectId, Long partId) {
        return projectPartRepository.findByProjectIdAndPartId(projectId, partId);
    }

    @Override
    public void deleteProjectPart(Long projectPartId) {
        projectPartRepository.deleteById(projectPartId);
    }
}

