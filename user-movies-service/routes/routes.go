package routes

import (
	"github.com/Dado555/glef.icu/user-movies-service/api"
	"github.com/gorilla/mux"
)

func CreateRoutes(api *api.API) *mux.Router {
	muxRouter := mux.NewRouter()

	// api
	a := muxRouter.PathPrefix("/api").Subrouter()

	// users
	u := a.PathPrefix("/lists").Subrouter()

	u.HandleFunc("/getWatchlist", api.GetWatchlist).Methods("GET") // page, size, userId
	u.HandleFunc("/getWishlist", api.GetWishlist).Methods("GET")   // page, size, userId

	u.HandleFunc("/getWatchlistItem", api.GetWatchlistItem).Methods("GET") // userId, movieId
	u.HandleFunc("/getWishlistItem", api.GetWishlistItem).Methods("GET")   // userId, movieId

	u.HandleFunc("/saveWatchlistItem", api.SaveWatchlistItem).Methods("POST") // userId, movieId
	u.HandleFunc("/saveWishlistItem", api.SaveWishlistItem).Methods("POST")   // userId, movieId

	u.HandleFunc("/deleteWatchlistItem", api.DeleteWatchlistItem).Methods("DELETE") // userId, movieId
	u.HandleFunc("/deleteWishlistItem", api.DeleteWishlistItem).Methods("DELETE")   // userId, movieId

	return muxRouter
}
