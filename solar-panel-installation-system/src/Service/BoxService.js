import axios from "axios";

const API_URL = "http://localhost:8080/api/box/";


//Összes rekesz lekérése
const findAll = () => {

    return axios.get(API_URL + "findall");
}

//Üres rekeszek lekérése
//Ezekben bármilyen típusú alkatrészt el tudok helyezni
//Rekeszek lekérése a rekeszben található alkatrész id szerint.
//Ezekben olyan típusú alkatrészt tudok elhelyezni, ami benne van. És persze ha van benne még szabad hely.
const findByPartId = (partid) => {

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