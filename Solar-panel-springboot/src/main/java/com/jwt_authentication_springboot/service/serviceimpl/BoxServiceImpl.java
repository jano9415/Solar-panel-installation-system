package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.*;
import com.jwt_authentication_springboot.repository.BoxRepository;
import com.jwt_authentication_springboot.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private PartServiceImpl partService;

    @Autowired
    private ProjectPartServiceImpl projectPartService;

    @Autowired
    private ProjectServiceImpl projectService;

    //Rekesz mentése
    @Override
    public void save(Box box) {
        boxRepository.save(box);

    }

    //Rekesz keresése id szerint
    @Override
    public ResponseEntity<Box> findById(long id) {

        return ResponseEntity.ok(boxRepository.findById(id).get());

    }

    //Összes rekesz lekérése
    @Override
    public List<Box> findAll() {
        return boxRepository.findAll();
    }

    //Kikeresem azokat a rekeszeket, amik tartalmazzák a paraméterben bejövő alkatrészt és van még benne szabad hely
    //Aztán kikeresem az üres rekeszeket
    //Ezeket egy listába gyűjtöm, majd visszatérek vele
    @Override
    public List<Box> findByPartId(Long partId) {
        List<Box> responseBoxes = new ArrayList<Box>();

        List<Box> boxes = findAll();

        for(Box b : boxes){
            if(b.getPart() == null){
                responseBoxes.add(b);
            }
            if(b.getPart() != null && b.getPart().equals(partService.findById(partId).getBody()) &&
            b.getNumberOfProducts() < b.getPart().getMaxPieceInBox()){
                responseBoxes.add(b);
            }
        }
        return responseBoxes;
    }

    //Alkatrész elhelyezése üres rekeszben
    //Átadom a rekesznek az alkatrészt, amit tárolni fog és az alkatrész mennyiségét
    //Növelem az alkatrész összesen elérhető darabszámát
    //Előfoglalások automatikus kezelése
    @Override
    public void placePartInEmptyBox(Long boxId, int placedAmount, Long partId) {
        Box actualBox = boxRepository.findById(boxId).get();
        Part part = partService.findById(partId).getBody();

        //Alkatrész elhelyezése előfoglalástól függetlenül
        actualBox.setNumberOfProducts(actualBox.getNumberOfProducts() + placedAmount);
        part.setAllAvailableNumber(part.getAllAvailableNumber() + placedAmount);
        actualBox.setPart(part);
        boxRepository.save(actualBox);

        //Alkatrész összes foglalt és előfoglalt mennyiség kezelése
        //Ha van az adott alkatrészre előfoglalás
        if(part.getPreReservedNumber() > 0){

            //Ha a bejövő mennyiség nagyobb vagy egyenlő, mint az előfoglalt mennyiség
            if(placedAmount >= part.getPreReservedNumber()){
                part.setAllReservedNumber(part.getAllReservedNumber() + part.getPreReservedNumber());
                part.setPreReservedNumber(0);
            }
            else{
                part.setPreReservedNumber(part.getPreReservedNumber() - placedAmount);
                part.setAllReservedNumber(part.getAllReservedNumber() + placedAmount);
            }
            partService.save(part);
        }

        //ProjectPart objektumok rendezése előfoglalt alkatrész mennyiség szerint csökkenő sorrendbe
        //Collections.sort(part.getProjectParts());

        //Alkatrész és projekt összerendelések kezelése
        for(ProjectPart projectPart : part.getProjectParts()){
            //Ha van olyan összerendelés, ahol az előfoglalt mennyiség nagyobb, mint 0
            //és van még elhelyezendő alkatrész
            if(projectPart.getPreReservedNumber() > 0 && placedAmount > 0){
                //Ha a bejövő mennyiség nagyobb vagy egyenlő, mint az előfoglalt mennyiség
                if(placedAmount >= projectPart.getPreReservedNumber()){
                    projectPart.setNumberOfParts(projectPart.getNumberOfParts() + projectPart.getPreReservedNumber());
                    placedAmount = placedAmount - projectPart.getPreReservedNumber();
                    projectPart.setPreReservedNumber(0);

                }
                else{
                    projectPart.setPreReservedNumber(projectPart.getPreReservedNumber() - placedAmount);
                    projectPart.setNumberOfParts(projectPart.getNumberOfParts() + placedAmount);
                    placedAmount = 0;
                }

            }
            projectPartService.save(projectPart);
        }
    }

    //Alkatrész elhelyezése nem üres rekeszben
    //A rekeszben már van ilyen típusú alkatrész, ezért csak a mennyiséget adom át
    //Növelem az alkatrész összesen elérhető darabszámát
    //Előfoglalások automatikus kezelése
    //A két bevételező függvényt egybe kell majd kezelni
    @Override
    public void placePartInBox(Long boxId, int placedAmount) {

        Box actualBox = boxRepository.findById(boxId).get();
        Part part = actualBox.getPart();

        actualBox.setNumberOfProducts(actualBox.getNumberOfProducts() + placedAmount);
        part.setAllAvailableNumber(part.getAllAvailableNumber() + placedAmount);
        boxRepository.save(actualBox);

        //Alkatrész elhelyezése előfoglalástól függetlenül
        actualBox.setNumberOfProducts(actualBox.getNumberOfProducts() + placedAmount);
        part.setAllAvailableNumber(part.getAllAvailableNumber() + placedAmount);
        actualBox.setPart(part);
        boxRepository.save(actualBox);

        //Alkatrész összes foglalt és előfoglalt mennyiség kezelése
        //Ha van az adott alkatrészre előfoglalás
        if(part.getPreReservedNumber() > 0){

            //Ha a bejövő mennyiség nagyobb vagy egyenlő, mint az előfoglalt mennyiség
            if(placedAmount >= part.getPreReservedNumber()){
                part.setAllReservedNumber(part.getAllReservedNumber() + part.getPreReservedNumber());
                part.setPreReservedNumber(0);
            }
            else{
                part.setPreReservedNumber(part.getPreReservedNumber() - placedAmount);
                part.setAllReservedNumber(part.getAllReservedNumber() + placedAmount);
            }
            partService.save(part);
        }

        //Alkatrész és projekt összerendelések kezelése
        for(ProjectPart projectPart : part.getProjectParts()){
            //Ha van olyan összerendelés, ahol az előfoglalt mennyiség nagyobb, mint 0
            //és van még elhelyezendő alkatrész
            if(projectPart.getPreReservedNumber() > 0 && placedAmount > 0){
                //Ha a bejövő mennyiség nagyobb vagy egyenlő, mint az előfoglalt mennyiség
                if(placedAmount >= projectPart.getPreReservedNumber()){
                    projectPart.setNumberOfParts(projectPart.getNumberOfParts() + projectPart.getPreReservedNumber());
                    placedAmount = placedAmount - projectPart.getPreReservedNumber();
                    projectPart.setPreReservedNumber(0);

                }
                else{
                    projectPart.setPreReservedNumber(projectPart.getPreReservedNumber() - placedAmount);
                    projectPart.setNumberOfParts(projectPart.getNumberOfParts() + placedAmount);
                    placedAmount = 0;
                }

            }
            projectPartService.save(projectPart);
        }

    }

    //Rekeszek lekérése a benne elhelyezkedő alkatrész id szerint
    @Override
    public List<Box> findBoxesByPartId(Long partId) {

        List<Box> boxes = new ArrayList<Box>();

        for(Box b : findAll()){
            if(b.getPart() != null && b.getPart().equals(partService.findById(partId).getBody())){
                boxes.add(b);
            }
        }

        return boxes;

    }

    //Alkatrész kivétele a rekeszből
    //Rekeszben lévő alkatérsz mennyiségének csökkentése. Ha eléri a nullát, akkor megszüntetem az alkatrész és rekesz
    //összerendelést
    //Összesen elérhető alkatrész mennyiségének csökkentése
    //Összesen lefoglalt alkatrész mennyiségének csökkentése
    //Projekt és alkatrész összerendelés frissítése. Ha eléri a nullát az alkatrész száma, akkor
    //megszüntetem az alkatrész és a projekt összerendelését
    //Új státuszt adok a projekthez: "InProgress" státuszt
    @Override
    public void takePart(Long boxId, int numberOfPart, int selectedNumberOfPart, Long projectId) {

        Box box = findById(boxId).getBody();
        Part part = box.getPart();
        Project project = projectService.findById(projectId).getBody();
        ProjectPart projectPart = projectPartService.findByProjectIdAndPartId(projectId, part.getId());

        //Új "InProgress" státusz hozzáadása a projekthez
        projectService.addNewProjectStatus(projectId, "InProgress");

        //Rekeszben megtalálható mennyiség csökkentése
        box.setNumberOfProducts(box.getNumberOfProducts() - selectedNumberOfPart);

        //Összerendelés megszüntetése, ha eléri a nullát
        if(box.getNumberOfProducts() == 0){
            box.setPart(null);
        }
        save(box);

        //Összesen elérhető alkatrész mennyiségének csökkentése
        part.setAllAvailableNumber(part.getAllAvailableNumber() - selectedNumberOfPart);

        //Összesen lefoglalt alkatrész mennyiségének csökkentése
        part.setAllReservedNumber(part.getAllReservedNumber() - selectedNumberOfPart);

        //Projekt és alkatrész összerendelés frissítése
        projectPart.setNumberOfParts(projectPart.getNumberOfParts() - selectedNumberOfPart);
        projectPartService.save(projectPart);

        if(projectPart.getNumberOfParts() == 0){
            projectPartService.deleteProjectPart(projectPart.getId());
        }
    }


}
