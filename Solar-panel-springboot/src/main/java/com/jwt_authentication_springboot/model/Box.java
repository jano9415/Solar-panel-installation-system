package com.jwt_authentication_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Box {

    @Id
    @GeneratedValue
    private Long id;

    //Many to one kapcsolat a Box és a Part között
    //Ez az osztály a birtokos, ez tartalmazza az idegen kulcsot
    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    //One to one kapcsolat a Box és a Location között
    //Ez az osztály a birtokos, ez tartalmazza az idegen kulcsot
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private int numberOfProducts;
}
