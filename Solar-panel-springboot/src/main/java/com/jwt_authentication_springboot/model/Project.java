package com.jwt_authentication_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String projectLocation;
    private String projectDescription;
    private String customerData;
    private float workDuration;
    private int workCost;

    //Many to one kapcsolat a Project és a ProjectStatus között
    //Ez az osztály a birtokos, ez tartalmazza az idegen kulcsot
    @ManyToOne
    @JoinColumn(name = "projectstatus_id")
    private ProjectStatus projectStatus;

    //Project Part kapcsolótábla
    @OneToMany(mappedBy = "project")
    private Set<ProjectPart> projectParts = new HashSet<ProjectPart>();


}
