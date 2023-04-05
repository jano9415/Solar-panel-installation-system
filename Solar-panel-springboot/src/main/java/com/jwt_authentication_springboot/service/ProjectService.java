package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    public void save(Project project);

    public ResponseEntity<Project> findById(long id);

    List<Project> findAll();

    void update(Long id, Project project);

    void reservePart(Long projectId, Long partId, int reservedNumber);

    void preReservePart(Long projectId, Long partId, int preReservedNumber);

    //Árkalkuláció elkészítése. Ha minden alkatrész elérhető a raktárban, visszatér a költséggel és a project "scheduled" fázisba kerül.
    //Ha nem érhető el minden alkatrész, akkor a költség 0 és a project "wait" fázisba kerül
    int showFullCost(Long projectId);

    void finishProject(Long projectId);

    //Projektek listázása kivételezésre
    //Csak olyan projekteket adok vissza, amelyeknek a kivételezését meg lehet kezdeni
    //Tehát az adott projekthez nem tartozik előfoglalt alkatrész
    List<Project> listProjectsWithoutPreReservation();

    //A kiválasztott projekthez tartozó lefoglalt alkatrészek listázása
    //Ezeket az alkatrészeket már ki lehet venni a raktárból
    List<Part> showPartsOfProject(Long projectId);
}
