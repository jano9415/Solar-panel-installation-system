package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BoxService {


    public void save(Box box);

    public ResponseEntity<Box> findById(long id);

    List<Box> findAll();

    List<Box> findByPartId(Long partId);

    void placePartInEmptyBox(Long boxId, int placedAmount, Long partId);

    void placePartInBox(Long boxId, int placedAmount);
}
