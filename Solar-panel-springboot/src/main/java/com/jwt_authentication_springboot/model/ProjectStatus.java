package com.jwt_authentication_springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProjectStatus {

    @Id
    @GeneratedValue
    private Long id;

    private String projectCurrentStatus;
    private String statusChanged;

    //Project ProjectStatus kapcsolótábla
    //Many to one kapcsolat
    //Ez az osztály osztály a birtokos
    @JsonIgnore
    @ManyToOne()
    @JoinTable(
            name = "project_projectstatus",
            joinColumns = {@JoinColumn(name = "projectstatus_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Project project;

}
