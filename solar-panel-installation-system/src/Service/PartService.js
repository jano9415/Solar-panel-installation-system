import axios from "axios";

const API_URL = "http://localhost:8080/api/part/";


//Új alkatrész hozzáadása
const createPart = (part) => {

    return axios.post(API_URL + "save", part);
};

//Alkatrész módosítása
const modifyPart = (id, part) => {

    return axios.put(API_URL + "update/" + id, part);

};

//Keresés id szerint
const findById = (id) => {

    return axios.get(API_URL + "findbyid/" + id);

};

//Összes alkatrész lekérése
const getParts = () => {

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