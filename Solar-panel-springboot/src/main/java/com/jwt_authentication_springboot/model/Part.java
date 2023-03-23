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
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"projectParts"})
public class Part implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String partName;

    private int price;

    private int maxPieceInBox;
    private int allAvailableNumber;
    private int allReservedNumber;
    private int preReservedNumber;

    //Project Part kapcsolótábla
    @OneToMany(mappedBy = "part")
    private Set<ProjectPart> projectParts = new HashSet<ProjectPart>();
}
