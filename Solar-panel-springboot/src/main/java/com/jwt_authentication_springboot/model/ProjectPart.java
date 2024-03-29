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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"project" , "part"})
public class ProjectPart implements Serializable, Comparable<ProjectPart> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "part_id")
    private Part part;

    private int numberOfParts;

    private int preReservedNumber;

    //ProjectPart objektumok rendezése előfoglalt alkatrész mennyiség szerint csökkenő sorrendbe
    @Override
    public int compareTo(ProjectPart o) {
        return o.getPreReservedNumber() - this.getPreReservedNumber();
    }

}
