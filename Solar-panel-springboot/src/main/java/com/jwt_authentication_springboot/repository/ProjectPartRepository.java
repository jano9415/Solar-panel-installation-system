package com.jwt_authentication_springboot.repository;

import com.jwt_authentication_springboot.model.ProjectPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPartRepository extends CrudRepository<ProjectPart, Long> {

    @Override
    List<ProjectPart> findAll();

    ProjectPart findByProjectIdAndPartId(Long projectId, Long partId);
}
