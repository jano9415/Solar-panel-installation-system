package com.jwt_authentication_springboot.service;

import com.jwt_authentication_springboot.model.Box;
import com.jwt_authentication_springboot.model.Location;
import org.springframework.http.ResponseEntity;

public interface LocationService {

    public void save(Location location);

    public ResponseEntity<Location> findById(long id);
}
