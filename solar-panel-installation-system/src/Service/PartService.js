import axios from "axios";

const API_URL = "http://localhost/";

//Új alkatrész hozzáadása
const createPart = (part) => {
    return axios.post(API_URL, {
        part
    });
};

//Alkatrész módosítása
const modifyPart = () => {

};

//Keresés id szerint
const findById = (id) => {
    //Teszt adat
    let part = {
        id: 2,
        partName: "Yamaha inverter",
        price: 56000,
        maxPieceInBox: 1
    }
    return part;
    //return axios.get(API_URL + id);

};

//Összes alkatrész lekérése
const getParts = () => {
    //Teszt adatok
    let parts = [
        {
            id: 1,
            partName: "Omron inverter",
            price: 18000,
            maxPieceInBox: 3
        },
        {
            id: 2,
            partName: "Yamaha inverter",
            price: 56000,
            maxPieceInBox: 1
        },
        {
            id: 3,
            partName: "Small panel",
            price: 12000,
            maxPieceInBox: 5
        },
        {
            id: 4,
            partName: "Medium panel",
            price: 24000,
            maxPieceInBox: 3
        },
        {
            id: 5,
            partName: "Big panel",
            price: 143000,
            maxPieceInBox: 1
        },
    ]

    return parts;
}


const PartService = {
    createPart,
    getParts,
    modifyPart,
    findById

};

export default PartService;