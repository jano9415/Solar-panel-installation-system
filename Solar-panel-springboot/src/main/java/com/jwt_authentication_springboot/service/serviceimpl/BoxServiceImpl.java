package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.repository.BoxRepository;
import com.jwt_authentication_springboot.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private PartServiceImpl partService;

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
    @Override
    public void placePartInEmptyBox(Long boxId, int placedAmount, Long partId) {
        Box actualBox = boxRepository.findById(boxId).get();
        Part part = partService.findById(partId).getBody();


        actualBox.setNumberOfProducts(actualBox.getNumberOfProducts() + placedAmount);
        part.setAllAvailableNumber(part.getAllAvailableNumber() + placedAmount);
        actualBox.setPart(part);
        boxRepository.save(actualBox);
    }

    //Alkatrész elhelyezése nem üres rekeszben
    //A rekeszben már van ilyen típusú alkatrész, ezért csak a mennyiséget adom át
    @Override
    public void placePartInBox(Long boxId, int placedAmount) {

        Box actualBox = boxRepository.findById(boxId).get();
        Part part = actualBox.getPart();

        actualBox.setNumberOfProducts(actualBox.getNumberOfProducts() + placedAmount);
        part.setAllAvailableNumber(part.getAllAvailableNumber() + placedAmount);
        boxRepository.save(actualBox);

    }
}
