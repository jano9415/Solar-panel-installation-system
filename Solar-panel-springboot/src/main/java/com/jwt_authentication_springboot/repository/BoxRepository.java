package com.jwt_authentication_springboot.repository;

import com.jwt_authentication_springboot.model.Box;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends CrudRepository<Box, Long> {

    @Override
    List<Box> findAll();
}
