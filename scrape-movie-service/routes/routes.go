package routes

import (
	"github.com/Dado555/glef.icu/scrape-movie-service/api"
	"github.com/gorilla/mux"
)

func CreateRoutes(api *api.API) *mux.Router {
	muxRouter := mux.NewRouter()

	// api
	a := muxRouter.PathPrefix("/api").Subrouter()

	// users
	u := a.PathPrefix("/movie").Subrouter()
	u.HandleFunc("/getByTitle", api.FindMovieByTitle).Methods("POST")
	u.HandleFunc("/saveMovie", api.SaveMovie).Methods("POST")
	u.HandleFunc("/getMovies", api.GetMovies).Methods("GET")
	u.HandleFunc("/getAllMovies", api.GetAllMovies).Methods("GET")
	u.HandleFunc("/getByImdbId/{imdbId}", api.GetMovieByImdbId).Methods("GET")
	u.HandleFunc("/watchMagnet", api.WatchMagnet).Methods("POST")
	u.HandleFunc("/searchMovies", api.SearchMovies).Methods("GET")
	u.HandleFunc("/updateMovie/{movieId}", api.UpdateMovie).Methods("PUT")
	u.HandleFunc("/searchTags", api.SearchTags).Methods("GET")

	return muxRouter
}
