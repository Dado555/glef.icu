import axios from "axios";

const API_URL = 'http://localhost:3001/api/movie/'
const GET_MOVIE_BY_TITLE = API_URL + 'getByTitle'
const SAVE_MOVIE = API_URL + "saveMovie"
const GET_MOVIES_PARAMS = API_URL + "getMovies"
const GET_ALL_MOVIES = API_URL + "getAllMovies"

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

    getMovies(params) {
        let newParams = { ...params };

        for (let key in params) {
            if (Array.isArray(params[key])) {
                newParams[key] = params[key].join(",");
            }
        }

        return axios.get(GET_MOVIES_PARAMS, {
            params: newParams,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }

    getAllMovies() {
        return axios.get(GET_ALL_MOVIES, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }
}

export const movieService = new MovieService();