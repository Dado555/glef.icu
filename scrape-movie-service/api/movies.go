package api

import (
	"encoding/json"
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"github.com/Dado555/glef.icu/scrape-movie-service/service"
	"net/http"
)

type MovieJSON struct {
	MovieTitle string `json:"title"`
	MovieYear  string `json:"year"`
}

func (api *API) FindMovieByTitle(w http.ResponseWriter, req *http.Request) {

	decoder := json.NewDecoder(req.Body)
	jsonData := MovieJSON{}
	err := decoder.Decode(&jsonData)

	if err != nil || jsonData.MovieTitle == "" || jsonData.MovieYear == "" {
		http.Error(w, "Wrong title or year", http.StatusBadRequest)
		return
	}

	movie := service.FindByTitle(jsonData.MovieTitle, jsonData.MovieYear)
	if movie.Title == "" {
		http.Error(w, "Movie not found", http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(movie)
	if err != nil {
		http.Error(w, "Could not get movie", http.StatusBadRequest)
		return
	}
}

func (api *API) SaveMovie(w http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	jsonData := models.Movie{}
	err := decoder.Decode(&jsonData)

	if err != nil || jsonData.Title == "" || jsonData.ImdbID == "" {
		http.Error(w, "Missing Title or ImdbID", http.StatusBadRequest)
		return
	}

	if api.movies.HasMovie(jsonData.ImdbID) {
		http.Error(w, "Movie with this ImdbID already exists", http.StatusBadRequest)
		return
	}

	movie := api.movies.AddMovie(jsonData)

	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(movie)
	if err != nil {
		http.Error(w, "Could not save movie", http.StatusBadRequest)
		return
	}
}
