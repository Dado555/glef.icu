import axios from "axios";

const API_URL = 'http://localhost:3000/api/user/'
const SEARCH_USERS = API_URL + 'searchUsers'
const GET_BY_USERNAME = API_URL + 'username'
const GET_BY_ID = API_URL + 'id'
const GET_USERS_PAGE = API_URL + 'getUsersPage'
const GET_BANNED_USERS_PAGE = API_URL + 'getBannedUsersPage'
const UPDATE_USER = API_URL + 'updateUser'
const BAN_USER = API_URL + 'banUser'

class UserService {
    searchUsers(searchTerm) {
        return axios.get(SEARCH_USERS, {
            params: searchTerm,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        })
    }

    getByUsername(name) {
        return axios.post(GET_BY_USERNAME, name, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    getById(id) {
        return axios.get(GET_BY_ID + `/${id}`,
            // {
            //     headers: {
            //         Authorization: "Bearer " + localStorage.getItem("id_token"),
            //     },
            // }
        );
    }

    getUsersPage(params) {
        let newParams = { ...params };

        for (let key in params) {
            if (Array.isArray(params[key])) {
                newParams[key] = params[key].join(",");
            }
        }

        return axios.get(GET_USERS_PAGE, {
            params: newParams,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }

    getBannedUsersPage(params) {
        let newParams = { ...params };

        for (let key in params) {
            if (Array.isArray(params[key])) {
                newParams[key] = params[key].join(",");
            }
        }

        return axios.get(GET_BANNED_USERS_PAGE, {
            params: newParams,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }

    updateUser(userId, payload) {
        return axios.put(UPDATE_USER + `/${userId}`, payload, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }

    banUser(username, isBanned) {
        return axios.put(BAN_USER + `/${username}/${isBanned}`, null,  {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }
}

export const userService = new UserService();