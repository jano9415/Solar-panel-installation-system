package com.jwt_authentication_springboot.payload.response;

import lombok.Data;

@Data
public class BestPathDTO implements Comparable<BestPathDTO> {

    private int row;

    private int col;

    private int cell;

    private String partName;

    private int partNumber;

    //Rendezés növekvő sorrendbe sor, oszlop majd rekesz szám szerint
    @Override
    public int compareTo(BestPathDTO o) {
        if(this.row != o.row){
            return this.row - o.row;
        }
        if(this.col != o.col){
            return this.col - o.col;
        }
        return this.cell - o.cell;
    }
}
