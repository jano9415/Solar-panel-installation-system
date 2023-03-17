import axios from "axios";

const API_URL = "http://localhost:8080/api/part";

//Teszt adatok
let parts = [
    {
        id: 1,
        partName: "Omron inverter",
        price: 18000,
        maxPieceInBox: 3,
        allAvailableNumber: 13,
        allReservedNumber: 4
    },
    {
        id: 2,
        partName: "Yamaha inverter",
        price: 56000,
        maxPieceInBox: 3,
        allAvailableNumber: 0,
        allReservedNumber: 0
    },
    {
        id: 3,
        partName: "Small panel",
        price: 12000,
        maxPieceInBox: 5,
        allAvailableNumber: 0,
        allReservedNumber: 2
    },
    {
        id: 4,
        partName: "Medium panel",
        price: 24000,
        maxPieceInBox: 3,
        allAvailableNumber: 5,
        allReservedNumber: 1
    },
    {
        id: 5,
        partName: "Big panel",
        price: 143000,
        maxPieceInBox: 1,
        allAvailableNumber: 17,
        allReservedNumber: 5
    },
]

//Új alkatrész hozzáadása
const createPart = (part) => {

    //Teszt
    parts.push(part);

    /*return axios.post(API_URL, {
        part
    });*/
};

//Alkatrész módosítása
const modifyPart = (id, part) => {
    //Teszt
    const searchIndex = parts.findIndex((part) => part.id == id);

    parts[searchIndex].partName = part.partName;
    parts[searchIndex].price = part.price;
    parts[searchIndex].maxPieceInBox = part.maxPieceInBox;

    /*return axios.post(API_URL + id, {
    part
});*/

};

//Keresés id szerint
const findById = (id) => {
    //Teszt
    const searchIndex = parts.findIndex((part) => part.id == id);
    return parts[searchIndex];

    //return axios.get(API_URL + id);

};

//Összes alkatrész lekérése
const getParts = () => {
    //Teszt 
    return parts;

    //return axios.get(API_URL);
}


const PartService = {
    createPart,
    getParts,
    modifyPart,
    findById

};

export default PartService;