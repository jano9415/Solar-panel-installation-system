import axios from "axios";

const API_URL = "http://localhost:8080/api/box/";

//Teszt adatok
let boxes = [
    {
        id: 1,
        part:
        {
            id: 2,
            partName: "Yamaha inverter",
            price: 56000,
            maxPieceInBox: 3
        },
        wareHouse:
        {
            id: 1,
            row: 1,
            cell: 1,
            col: 1
        },
        numberOfProducts: 2
    },
    {
        id: 14,
        part:
        {
            id: 2,
            partName: "Yamaha inverter",
            price: 56000,
            maxPieceInBox: 3
        },
        wareHouse:
        {
            id: 8,
            row: 2,
            cell: 1,
            col: 3
        },
        numberOfProducts: 1
    },

]

let boxes2 = [
    {
        id: 1,
        part:
        {
            id: 2,
            partName: "Yamaha inverter",
            price: 56000,
            maxPieceInBox: 3
        },
        wareHouse:
        {
            id: 1,
            row: 1,
            cell: 1,
            col: 1
        },
        numberOfProducts: 2
    },
    {
        id: 14,
        part:
        {
            id: 2,
            partName: "Yamaha inverter",
            price: 56000,
            maxPieceInBox: 3
        },
        wareHouse:
        {
            id: 8,
            row: 2,
            cell: 1,
            col: 3
        },
        numberOfProducts: 1
    },
    {
        id: 1,
        part:
        {

        },
        wareHouse:
        {
            id: 2,
            row: 1,
            cell: 2,
            col: 1
        },
        numberOfProducts: 0
    },
    {
        id: 14,
        part:
        {

        },
        wareHouse:
        {
            id: 3,
            row: 1,
            cell: 3,
            col: 1
        },
        numberOfProducts: 0
    },

]

//Összes rekesz lekérése
const findAll = () => {

    return axios.get(API_URL + "findall");
}

//Üres rekeszek lekérése
//Ezekben bármilyen típusú alkatrészt el tudok helyezni
//Rekeszek lekérése a rekeszben található alkatrész id szerint.
//Ezekben olyan típusú alkatrészt tudok elhelyezni, ami benne van. És persze ha van benne még szabad hely.
const findByPartId = (partid) => {


    //Teszt
    /*
    return boxes;
    return boxes2;
    */
    return axios.get(API_URL + "findbypartid/" + partid);
};

//Alkatrész elhelyezése, ha a rekeszben már van ilyen típusú alkatrész
//Elküldöm, hogy mennyi alkatrészt teszek még bele
const placePartInBox = (boxId, placedAmount) => {

    return axios.get(API_URL + "placepartinbox/" + boxId + "/" + placedAmount);

}

//Alkatrész elhelyezése, ha a rekesz még üres.
//Elküldöm az alkatrész objektumot és, hogy mennyi alkatrészt teszek bele.
const placePartInEmptyBox = (boxId, placedAmount, partId) => {

    return axios.get(API_URL + "placepartinemptybox/" + boxId + "/" + placedAmount + "/" + partId);

}

//Rekeszek lekérése a benne elhelyezkedő alkatrész id szerint
const findBoxesByPartId = (partId) => {

    return axios.get(API_URL + "findboxesbypartid/" + partId);
}

//Alkatrész kivétele a rekeszből
const takePart = (boxId, numberOfPart, selectedNumberOfPart, projectId) => {

    return axios.get(API_URL + "takepart/" + boxId + "/" + numberOfPart + "/" + selectedNumberOfPart + "/" + projectId)

}


const BoxService = {
    findByPartId,
    findAll,
    placePartInBox,
    placePartInEmptyBox,
    findBoxesByPartId,takePart

};

export default BoxService;