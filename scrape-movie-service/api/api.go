package api

import (
	"encoding/json"
	"github.com/Dado555/glef.icu/scrape-movie-service/service"
	"net/http"
)

type MovieJSON struct {
	MovieTitle string `json:"title"`
	MovieYear  string `json:"year"`
}

func FindMovieByTitle(w http.ResponseWriter, req *http.Request) {

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
		http.Error(w, "Could not get token", http.StatusBadRequest)
		return
	}

	//_, err = w.Write([]byte(movieJson))
	//if err != nil {
	//	http.Error(w, "Could not get token", http.StatusBadRequest)
	//	return
	//}
}
