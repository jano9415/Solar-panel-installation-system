import axios from "axios";

const API_URL = "http://localhost:8080/api/project";


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

    //return axios.get(API_URL);
    return projects;
}

//Új projekt létrehozása
const createProject = (project) => {

    return axios.post(API_URL, 
        {
            project
        })
}

const ProjectService = {
    findAll,
    createProject

};

export default ProjectService;