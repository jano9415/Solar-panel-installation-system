package com.jwt_authentication_springboot.controller;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.model.ProjectPart;
import com.jwt_authentication_springboot.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/part")
public class PartController {

    @Autowired
    private PartService partService;

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Part> findById(@PathVariable Long id){
        return partService.findById(id);
    }

    @GetMapping("/findall")
    public List<Part> findAll(){
        return partService.findAll();
    }

    @GetMapping("/findall2")
    public List<Part> findAll2(){
        List<Part> parts = partService.findAll();

        for(Part p : parts){
            for(ProjectPart pp : p.getProjectParts()){

            }
        }

        return parts;
    }

    @PostMapping("/save")
    public void save(@RequestBody Part part){
        partService.save(part);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Part part){
        partService.update(id, part);
    }

    //Hiányzó alkatrészek lekérése
    @GetMapping("/findlackofparts")
    public List<Part> findLackOfParts(){
        return partService.findLackOfParts();

    }

    //Hiányzó és előfoglalt alkatrészek lekérése
    @GetMapping("/findlackofpartswithprereservation")
    public List<Part> findLackOfPartsWithPreReservation(){
        return partService.findLackOfPartsWithPreReservation();

    }


}
