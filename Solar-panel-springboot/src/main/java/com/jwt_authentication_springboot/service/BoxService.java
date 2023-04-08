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

    //Rekeszek lekérése a benne elhelyezkedő alkatrész id szerint
    List<Box> findBoxesByPartId(Long partId);

    //Alkatrész kivétele a rekeszből
    void takePart(Long boxId, int numberOfPart, int selectedNumberOfPart, Long projectId);
}
