package com.jwt_authentication_springboot.service.serviceimpl;

import com.jwt_authentication_springboot.model.Location;
import com.jwt_authentication_springboot.repository.LocationRepository;
import com.jwt_authentication_springboot.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public void save(Location location) {

        locationRepository.save(location);

    }

    @Override
    public ResponseEntity<Location> findById(long id) {
        return ResponseEntity.ok(locationRepository.findById(id).get());
    }
}
