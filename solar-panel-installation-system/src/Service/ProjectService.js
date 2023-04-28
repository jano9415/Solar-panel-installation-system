import axios from "axios";

const API_URL = "http://localhost:8080/api/project/";


//Összes project lekérése
const findAll = () => {

    return axios.get(API_URL + "findall");
}

//Új projekt létrehozása
const createProject = (project) => {

    return axios.post(API_URL + "createproject", project)
}

//Meglévő projekt módosítása
//Alkatrész módosítása
const modifyProject = (id, project) => {

    return axios.put(API_URL + "update/" + id, project);

};

//Keresés id szerint
const findById = (id) => {

    return axios.get(API_URL + "findbyid/" + id);

};

//Alkatrész lefoglalása
const reservePart = (projectId, partId, reservedNumber) => {

    return axios.get(API_URL + "reservepart/" + projectId + "/" + partId + "/" + reservedNumber)
}

//Előfoglalás leadása az alkatrészre
const preReservePart = (projectId, partId, preReservedNumber) => {
    return axios.get(API_URL + "prereservepart/" + projectId + "/" + partId + "/" + preReservedNumber)

}

//Árkalkuláció elkészítése. Ha minden alkatrész elérhető a raktárban, visszatér a költséggel és a project "scheduled" fázisba kerül.
//Ha nem érhető el minden alkatrész, akkor a költség 0 és a project "wait" fázisba kerül
const showFullCost = (projectId) => {
    return axios.get(API_URL + "showfullcost/" + projectId)
}

//Projekt lezárása.
//Ha sikeres akkor "completed" fázsiba kerül. Ha nem, akkor "failed" fázisba.
const finishProject = (projectId, status) => {
    return axios.get(API_URL + "finishproject/" + projectId + "/" + status)
}

//Projektek listázása kivételezésre
const listProjectsWithoutPreReservation = () => {
    return axios.get(API_URL + "listprojectswithoutprereservation")
}

//A kiválasztott projekthez tartozó lefoglalt alkatrészek listázása
const showPartsOfProject = (projectId) => {
    return axios.get(API_URL + "showpartsofproject/" + projectId)
}

//Legjobb útvonal listázása
const bestPath = (projectId) => {
    return axios.get(API_URL + "bestpath/" + projectId)
}


//Projektek lekérése
//Folyamatban lévő projektek vagy lezárt projektek
const findByProjectStatus = (status) => {
    return axios.get(API_URL + "findbyprojectstatus/" + status)

}

const ProjectService = {
    findAll,
    createProject,
    modifyProject,
    findById,
    reservePart,
    preReservePart,
    showFullCost,
    finishProject,
    listProjectsWithoutPreReservation,
    showPartsOfProject,
    bestPath,
    findByProjectStatus

};

export default ProjectService;