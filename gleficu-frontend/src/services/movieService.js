import axios from "axios";

const API_URL = 'http://localhost:3001/api/movie/'
const GET_MOVIE_BY_TITLE = API_URL + 'getByTitle'
const SAVE_MOVIE = API_URL + "saveMovie"

class MovieService {
    getByTitle(movie) {
        return axios.post(GET_MOVIE_BY_TITLE, movie, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    saveMovie(movie) {
        return axios.post(SAVE_MOVIE, movie, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }
}

export const movieService = new MovieService();