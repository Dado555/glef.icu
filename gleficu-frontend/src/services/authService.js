import jwtDecode from "jwt-decode";
const axios = require("axios");

class AuthService {
    login(payload) {
        return axios.post(
            `${process.env.VUE_APP_BASE_PATH}/users/login`,
            payload
        );
    }

    setToken(jwt) {
        localStorage.setItem("jwt", jwt);
    }

    getRoles() {
        let jwt = localStorage.getItem("jwt");
        if (jwt) {
            let token = jwtDecode(jwt);
            return token.roles;
        } else return "";
    }

    userLoggedIn() {
        return localStorage.getItem("jwt") !== null;
    }

    removeJwt() {
        localStorage.removeItem("jwt");
    }

    userId() {
        let jwt = localStorage.getItem("jwt");
        if (jwt) {
            let token = jwtDecode(jwt);
            return token.userId;
        } else return -1;
    }
}

export const authService = new AuthService();