package com.jwt_authentication_springboot.payload.response;

import com.jwt_authentication_springboot.model.ProjectPart;
import lombok.Data;


@Data
public class PartDTO {

    private Long id;

    private String partName;

    private int price;

    private int maxPieceInBox;
    private int allAvailableNumber;
    private int allReservedNumber;
    private int preReservedNumber;

    private int numberOfParts;



}
