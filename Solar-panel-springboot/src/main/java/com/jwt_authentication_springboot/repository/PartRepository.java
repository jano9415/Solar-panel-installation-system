package com.jwt_authentication_springboot.repository;

import com.jwt_authentication_springboot.model.Part;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends CrudRepository<Part, Long> {

    @Override
    public List<Part> findAll();

}
