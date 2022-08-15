import axios from "axios";

const API_URL = 'http://localhost:3001/api/movie/'
const GET_MOVIE_BY_TITLE = API_URL + 'getByTitle'

class MovieService {
    getByTitle(movie) {
        return axios.post(GET_MOVIE_BY_TITLE, movie, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }
}

export const movieService = new MovieService();