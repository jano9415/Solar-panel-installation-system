package com.jwt_authentication_springboot.repository;

import com.jwt_authentication_springboot.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
