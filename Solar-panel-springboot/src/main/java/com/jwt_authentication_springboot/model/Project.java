package com.jwt_authentication_springboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"projectParts"})
public class Project implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String projectLocation;
    private String projectDescription;
    private String customerData;
    private float workDuration;
    private int workCost;

    //Project ProjectStatus kapcsolótábla
    //One to Many kapcsolat
    //A ProjectStatus osztály a birtokos
    @OneToMany(mappedBy = "project")
    private Set<ProjectStatus> projectStatuses = new HashSet<ProjectStatus>();

    //Project Part kapcsolótábla
    @OneToMany(mappedBy = "project")
    private Set<ProjectPart> projectParts = new HashSet<ProjectPart>();


}
