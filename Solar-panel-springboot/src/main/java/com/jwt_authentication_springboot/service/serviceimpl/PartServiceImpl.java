package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Location;
import com.jwt_authentication_springboot.model.Part;
import com.jwt_authentication_springboot.repository.PartRepository;
import com.jwt_authentication_springboot.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    PartRepository partRepository;


    @Override
    public List<Part> findAll() {
        return partRepository.findAll();
    }

    @Override
    public void save(Part part) {
        partRepository.save(part);

    }

    @Override
    public ResponseEntity<Part> findById(long id) {
        return ResponseEntity.ok(partRepository.findById(id).get());
    }

    @Override
    public void update(Long id, Part part) {
        Part modifedPart = partRepository.findById(id).get();

        modifedPart.setPartName(part.getPartName());
        modifedPart.setPrice(part.getPrice());
        modifedPart.setMaxPieceInBox(part.getMaxPieceInBox());

        partRepository.save(modifedPart);





    }

    //Hiányzó alkatrészek lekérése
    @Override
    public List<Part> findLackOfParts() {

        List<Part> parts = new ArrayList<Part>();

        for(Part p : findAll()){
            if( (p.getAllAvailableNumber() - p.getAllReservedNumber()) == 0 && p.getPreReservedNumber() == 0 ){
                parts.add(p);
            }
        }
        return parts;
    }

    //Hiányzó és előfoglalt alkatrészek lekérése
    @Override
    public List<Part> findLackOfPartsWithPreReservation() {
        List<Part> parts = new ArrayList<Part>();

        for(Part p : findAll()){
            if(p.getPreReservedNumber() > 0 ){
                parts.add(p);
            }
        }
        return parts;
    }
}
