package com.jwt_authentication_springboot.controller;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.service.serviceimpl.BoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/box")
public class BoxController {

    @Autowired
    private BoxServiceImpl boxService;

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Box> findById(@PathVariable Long id){
        return boxService.findById(id);
    }

    @GetMapping("/findall")
    public List<Box> findAll(){
        return boxService.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody Box box){
        boxService.save(box);
    }

    @GetMapping("/findbypartid/{partid}")
    public List<Box> findByPartId(@PathVariable Long partid){
        return boxService.findByPartId(partid);
    }

    @GetMapping("/placepartinemptybox/{boxId}/{placedAmount}/{partId}")
    public void placePartInEmptyBox(@PathVariable Long boxId, @PathVariable int placedAmount,
                                    @PathVariable Long partId)
    {
        boxService.placePartInEmptyBox(boxId,placedAmount,partId);
    }

    @GetMapping("/placepartinbox/{boxId}/{placedAmount}")
    public void placePartInBox(@PathVariable Long boxId, @PathVariable int placedAmount){
        boxService.placePartInBox(boxId, placedAmount);
    }

    //Rekeszek lekérése a benne elhelyezkedő alkatrész id szerint
    @GetMapping("/findboxesbypartid/{partId}")
    public List<Box> findBoxesByPartId(@PathVariable Long partId){
        return boxService.findBoxesByPartId(partId);
    }

    //Alkatrész kivétele a rekeszből
    @GetMapping("/takepart/{boxId}/{numberOfPart}/{selectedNumberOfPart}/{projectId}")
    public void takePart(@PathVariable Long boxId, @PathVariable int numberOfPart,
                         @PathVariable int selectedNumberOfPart, @PathVariable Long projectId){
        boxService.takePart(boxId, numberOfPart, selectedNumberOfPart, projectId);
    }
}
