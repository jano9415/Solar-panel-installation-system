package com.jwt_authentication_springboot.repository;

import com.jwt_authentication_springboot.model.ProjectStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepository extends CrudRepository<ProjectStatus, Long> {

}
