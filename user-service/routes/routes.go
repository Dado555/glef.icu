package routes

import (
	"github.com/Dado555/glef.icu/user-service/api"
	"github.com/Dado555/glef.icu/user-service/auth"
	"github.com/gorilla/mux"
	"github.com/urfave/negroni"
	"net/http"
)

func CreateRoutes(api *api.API) *mux.Router {
	muxRouter := mux.NewRouter()

	// api
	a := muxRouter.PathPrefix("/api").Subrouter()

	// users
	u := a.PathPrefix("/user").Subrouter()
	u.HandleFunc("/signup", api.UserSignup).Methods("POST")
	u.HandleFunc("/login", api.UserLogin).Methods("POST")
	u.Handle("/info", negroni.New(
		negroni.HandlerFunc(auth.JwtMiddleware.HandlerWithNext),
		negroni.Wrap(http.HandlerFunc(api.UserInfo)),
	))

	return muxRouter
}