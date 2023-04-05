import axios from "axios";

const API_URL = "http://localhost:8080/api/part/";

//Teszt adatok

let parts = [
    {
        id: 1,
        partName: "Omron inverter",
        price: 18000,
        maxPieceInBox: 3,
        allAvailableNumber: 13,
        allReservedNumber: 4,
        preReservedNumber: 0
    },
    {
        id: 2,
        partName: "Yamaha inverter",
        price: 56000,
        maxPieceInBox: 3,
        allAvailableNumber: 0,
        allReservedNumber: 0,
        preReservedNumber: 3
    },
    {
        id: 3,
        partName: "Small panel",
        price: 12000,
        maxPieceInBox: 5,
        allAvailableNumber: 0,
        allReservedNumber: 0,
        preReservedNumber: 1
    },
    {
        id: 4,
        partName: "Medium panel",
        price: 24000,
        maxPieceInBox: 3,
        allAvailableNumber: 5,
        allReservedNumber: 1,
        preReservedNumber: 0
    },
    {
        id: 5,
        partName: "Big panel",
        price: 143000,
        maxPieceInBox: 1,
        allAvailableNumber: 17,
        allReservedNumber: 5,
        preReservedNumber: 0
    },
]

//Új alkatrész hozzáadása
const createPart = (part) => {

    //Teszt
    //parts.push(part);

    return axios.post(API_URL + "save", part);
};

//Alkatrész módosítása
const modifyPart = (id, part) => {
    //Teszt
    /*
    const searchIndex = parts.findIndex((part) => part.id == id);

    parts[searchIndex].partName = part.partName;
    parts[searchIndex].price = part.price;
    parts[searchIndex].maxPieceInBox = part.maxPieceInBox;
    */

    return axios.put(API_URL + "update/" + id, part);

};

//Keresés id szerint
const findById = (id) => {
    //Teszt
    /*
    const searchIndex = parts.findIndex((part) => part.id == id);
    return parts[searchIndex];
    */

    return axios.get(API_URL + "findbyid/" + id);

};

//Összes alkatrész lekérése
const getParts = () => {
    //Teszt 
    //return parts;

    return axios.get(API_URL + "findall");
}

//Hiányzó alkatrészek lekérése
const findLackOfParts = () => {
    return axios.get(API_URL + "findlackofparts")
}

//Hiányzó és előfoglalt alkatrészek lekérése
const findLackOfPartsWithPreReservation = () => {
    return axios.get(API_URL + "findlackofpartswithprereservation")
}


const PartService = {
    createPart,
    getParts,
    modifyPart,
    findById,
    findLackOfParts,
    findLackOfPartsWithPreReservation

};

export default PartService;