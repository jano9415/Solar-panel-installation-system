package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PartService {

    public List<Part> findAll();

    public void save(Part part);

    public ResponseEntity<Part> findById(long id);

    void update(Long id, Part part);
}
