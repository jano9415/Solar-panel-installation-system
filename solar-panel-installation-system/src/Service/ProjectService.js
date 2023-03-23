import axios from "axios";

const API_URL = "http://localhost:8080/api/project/";


//Teszt adatok
let projects = [
    {
        id: 1,
        projectLocation: "Veszprém",
        projectDescription: "6 darab panel telepítése",
        customerData: "Nagy József",
        workDuration: 3.5,
        workCost: 320000,

        projectStatus:
        {
            id: 1,
            projectCurrentStatus: "new",
            statusChanged: "2023-03-17"
        },
    },
    {
        id: 2,
        projectLocation: "Székesfehérvár",
        projectDescription: "4 darab panel telepítése",
        customerData: "Alba Kft",
        workDuration: 2.5,
        workCost: 210000,

        projectStatus:
        {
            id: 1,
            projectCurrentStatus: "new",
            statusChanged: "2023-03-15"
        },
    },
    {
        id: 3,
        projectLocation: "Tapolca",
        projectDescription: "Inverter telepítése",
        customerData: "Nagy András",
        workDuration: 1,
        workCost: 134000,

        projectStatus:
        {
            id: 2,
            projectCurrentStatus: "draft",
            statusChanged: "2023-03-10"
        },
    },
]


//Összes project lekérése
const findAll = () => {

    return axios.get(API_URL + "findall");
    //return projects;
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
    //Teszt
    //const searchIndex = projects.findIndex((project) => project.id == id);
    //return projects[searchIndex];

    return axios.get(API_URL + "findbyid/" + id);

};

//Alkatrész lefoglalása
const reservePart = (projectId, partId, reservedNumber) => {

    return axios.get(API_URL + "reservepart/" +  projectId + "/" + partId + "/" + reservedNumber)
}

//Előfoglalás leadása az alkatrészre
const preReservePart = (projectId, partId, preReservedNumber) => {
    return axios.get(API_URL + "prereservepart/" + projectId + "/" +  partId + "/" + preReservedNumber)

}

const ProjectService = {
    findAll,
    createProject,
    modifyProject,
    findById,
    reservePart,
    preReservePart

};

export default ProjectService;