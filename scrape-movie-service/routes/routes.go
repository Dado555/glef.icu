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

	return muxRouter
}
