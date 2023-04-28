package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.model.Project;
import com.jwt_authentication_springboot.payload.response.BestPathDTO;
import com.jwt_authentication_springboot.payload.response.PartDTO;
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

    //Projekt lezárása
    //A bejövő paraméter "success" vagy "unsuccess"
    //Ha sikeres akkor "completed" fázsiba kerül, ha nem, akkor "failed" fázisba.
    void finishProject(Long projectId, String status);

    //Projektek listázása kivételezésre
    //Csak olyan projekteket adok vissza, amelyeknek a kivételezését meg lehet kezdeni
    //Tehát az adott projekthez nem tartozik előfoglalt alkatrész
    List<Project> listProjectsWithoutPreReservation();

    //A kiválasztott projekthez tartozó lefoglalt alkatrészek listázása
    //Ezeket az alkatrészeket már ki lehet venni a raktárból
    List<PartDTO> showPartsOfProject(Long projectId);

    //Legjobb útvonal megkeresése az alkatrészek összegyűjtéséhez
    List<BestPathDTO> bestPath(Long projectId);

    //Projektek lekérése
    //Folyamatban lévő projektek vagy lezárt projektek
    List<Project> findByProjectStatus(String status);
}
