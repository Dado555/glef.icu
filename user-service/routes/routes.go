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
	u.HandleFunc("/searchUsers", api.UserSearch).Methods("GET")
	u.HandleFunc("/username/{username}", api.GetUserByUsername).Methods("GET")
	u.HandleFunc("/id/{id}", api.GetUserById).Methods("GET")
	u.HandleFunc("/getUsersPage", api.GetUsersPage).Methods("GET")
	u.Handle("/info", negroni.New(
		negroni.HandlerFunc(auth.JwtMiddleware.HandlerWithNext),
		negroni.Wrap(http.HandlerFunc(api.UserInfo)),
	))

	// admin
	// u.HandleFunc("/ban/{username}", api.BanUser).Methods("GET")

	// other services
	// u.HandleFunc("/adminAuthorize", api.AuthorizeAdmin).Methods("GET")
	// u.HandleFunc("/userAuthorize", api.AuthorizeUser).Methods("GET")

	return muxRouter
}
