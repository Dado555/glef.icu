import axios from "axios";

const API_URL = 'http://localhost:3000/api/user/'
const SEARCH_USERS = API_URL + 'searchUsers'

class UserService {
    searchUsers(searchTerm) {
        return axios.get(SEARCH_USERS, {
            params: searchTerm,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        })
    }
}

export const userService = new UserService();