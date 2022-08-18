import axios from "axios";

const API_URL = 'http://localhost:3001/api/movie/'
const GET_MOVIE_BY_TITLE = API_URL + 'getByTitle'
const SAVE_MOVIE = API_URL + "saveMovie"
const GET_MOVIES_PARAMS = API_URL + "getMovies"
const GET_ALL_MOVIES = API_URL + "getAllMovies"
const GET_MOVIE_BY_IMDB_ID = API_URL + "getByImdbId"
const WATCH_MAGNET = API_URL + "watchMagnet"
const SEARCH_MOVIES = API_URL + "searchMovies"
const UPDATE_MOVIE = API_URL + "updateMovie"

class MovieService {
    getByTitle(movie) {
        return axios.post(GET_MOVIE_BY_TITLE, movie, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    saveMovie(movie) {
        console.log(movie);
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

    getMovieByImdbId(imdbId) {
        return axios.get(GET_MOVIE_BY_IMDB_ID + `/${imdbId}`, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        })
    }

    watchMagnet(watchLive) {
        return axios.post(WATCH_MAGNET, watchLive,{
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        })
    }

    searchMovies(searchTerm) {
        return axios.get(SEARCH_MOVIES, {
            params: searchTerm,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        })
    }

    updateMovie(movieId, payload) {
        return axios.put(UPDATE_MOVIE + `/${movieId}`, payload, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            },
        });
    }
}

export const movieService = new MovieService();