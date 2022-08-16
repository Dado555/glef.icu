package api

import (
	"encoding/json"
	"fmt"
	"github.com/Dado555/glef.icu/scrape-movie-service/downloadPlay"
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"github.com/Dado555/glef.icu/scrape-movie-service/service"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
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

func (api *API) GetMovies(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	page := queryParams.Get("page")
	size := queryParams.Get("size")

	pageParsed, _ := strconv.ParseUint(page, 10, 64)
	sizeParsed, _ := strconv.ParseUint(size, 10, 64)

	movies := api.movies.GetMovies(pageParsed, sizeParsed)

	moviePage := models.MoviePage{
		Movies: movies,
	}

	err := json.NewEncoder(w).Encode(moviePage)
	if err != nil {
		http.Error(w, "Could not return movies list", http.StatusBadRequest)
		return
	}
}

func (api *API) GetAllMovies(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	movies := api.movies.GetAllMovies()

	moviePage := models.MoviePage{
		Movies: movies,
	}

	err := json.NewEncoder(w).Encode(moviePage)
	if err != nil {
		http.Error(w, "Could not return movies list", http.StatusBadRequest)
		return
	}
}

func (api *API) GetMovieByImdbId(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(req)
	imdbId := params["imdbId"]

	movie := api.movies.FindMovieByImdbID(imdbId)
	if movie == nil {
		http.Error(w, "Could not find movie", http.StatusBadRequest)
		return
	}

	err := json.NewEncoder(w).Encode(movie)
	if err != nil {
		http.Error(w, "Could not return movie", http.StatusBadRequest)
		return
	}
}

type WatchLive struct {
	MagnetLink   string `json:"magnetLink"`
	SubtitleLink string `json:"subtitleLink"`
}

func (api *API) WatchMagnet(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	decoder := json.NewDecoder(req.Body)
	jsonData := WatchLive{}
	err := decoder.Decode(&jsonData)

	if err != nil || jsonData.MagnetLink == "" { // || jsonData.subtitleLink == ""
		http.Error(w, "Missing magnet link or subtitle link", http.StatusBadRequest)
		return
	}

	//folderName := DownloadSubtitle()
	//subtitlePath := getSubtitleFilePath(folderName)
	//fmt.Println(subtitlePath)
	downloadPlay.HandleWatchTorrentMagnet(jsonData.MagnetLink)
}

func (api *API) SearchMovies(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)
	title := queryParams.Get("title")
	movies := api.movies.SearchMovies(title)

	err := json.NewEncoder(w).Encode(movies)
	if err != nil {
		http.Error(w, "Could not return movies list", http.StatusBadRequest)
		return
	}
}
