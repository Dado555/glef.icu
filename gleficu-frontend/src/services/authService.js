import jwtDecode from "jwt-decode";
import axios from "axios";

const API_URL = 'http://localhost:3000/api/user/'
const LOGIN_URL = API_URL + 'login'
const SIGNUP_URL = API_URL + 'signup'

class AuthService {
    login (credentials) {
        return axios.post(LOGIN_URL, credentials, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    signup (credentials) {
        return axios.post(SIGNUP_URL, credentials, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    logout (context) {
        localStorage.removeItem('id_token')
        context.$router.replace('/')
    }

    isAuthenticated () {
        let jwt = localStorage.getItem('id_token')
        if (jwt) {
            return true
        }
        return false
    }

    isAdmin() {
        try {
            let username = this.getJwtField("username")
            let role = this.getJwtField("authority")
            if (username && role === "ADMIN")
                return true;
            return false;
        } catch (error) {
            return false;
        }
    }

    isUser() {
        try{
            let username = this.getJwtField("username")
            let role = this.getJwtField("authority")
            if (username && role === "USER")
                return true;
            return false;
        } catch (error) {
            return false;
        }
    }

    isUnregistered() {
        try {
            let username = this.getJwtField("username")
            let role = this.getJwtField("authority")
            if (!username && (!role || ( role !== "ADMIN" && role !== "USER")))
                return true;
            return false;
        } catch (error) {
            return true;
        }
    }

    getAuthHeader () {
        return {
            'Authorization': 'Bearer ' + localStorage.getItem('id_token')
        }
    }

    decodeJwt() {
        return jwtDecode(localStorage.getItem("id_token"));
    }

    setToken(jwt) {
        localStorage.setItem("id_token", jwt);
    }

    removeToken() {
        localStorage.removeItem("id_token");
    }

    getJwtField(fieldName) {
        const jwt = this.decodeJwt();
        // console.log(jwt);
        if (fieldName === "username") return jwt.username;
        else return jwt[fieldName];
    }
}

export const authService = new AuthService();