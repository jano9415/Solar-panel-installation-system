import axios from "axios";


const API_URL = "http://localhost:8080/api/auth/";

//Felhasználó létrehozása
const createUser = (username, email, password) => {
    return axios.post(API_URL + "signup", {
        username,
        email,
        password
    });
};

//Bejelentkezés
const login = (username, password) => {

    //Teszt adatok
    /*let expert = {
        userName: "bela",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "expert"
    }
    let storeEmployee = {
        userName: "balazs",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "storeemployee"
    }
    let storeLeader = {
        userName: "laci",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "storeleader"
    }
    let admin = {
        userName: "geza",
        firstName: "László",
        lastName: "Nagy",
        emailAddress: "lacika@gmail.com",
        role: "admin"
    }
    if(userName === "expert"){
        localStorage.setItem("user", JSON.stringify(expert));
    }
    if(userName === "storeemployee"){
        localStorage.setItem("user", JSON.stringify(storeEmployee));
    }
    if(userName === "storeleader"){
        localStorage.setItem("user", JSON.stringify(storeLeader));
    }
    if(userName === "admin"){
        localStorage.setItem("user", JSON.stringify(admin));
    }*/

    return axios.post(API_URL + "signin", {
        username,
        password
    })
        .then((response) => {
            localStorage.setItem("user", JSON.stringify(response.data));

        });
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