package com.jwt_authentication_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Part {

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
