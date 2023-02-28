import axios from "axios";


const API_URL = "http://localhost/";

//Felhasználó létrehozása
const createUser = (userName, emailAddress, password) => {
    return axios.post(API_URL, {
        userName,
        emailAddress,
        password
    });
};

//Bejelentkezés
const login = (userName, password) => {

    //Teszt adatok
    let expert = {
        userName: "laci",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "expert"
    }
    let storeEmployee = {
        userName: "laci",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "expert"
    }
    let storeLeader = {
        userName: "laci",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "expert"
    }
    localStorage.setItem("user", JSON.stringify(expert));

    /*return axios.post(API_URL, {
        userName,
        password
    })
        .then((response) => {
            localStorage.setItem("user", expert);

        });*/
};

//Kijelentkezés
const logout = () => {
    localStorage.removeItem("user");
}

//Aktuálisan bejelentkezett felhasználó lekérése
const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
}


const AuthService = {
    createUser,
    login,
    logout,
    getCurrentUser

};

export default AuthService;