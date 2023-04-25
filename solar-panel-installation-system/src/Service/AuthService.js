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