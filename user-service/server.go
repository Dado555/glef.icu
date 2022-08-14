package main

import (
	api2 "github.com/Dado555/glef.icu/user-service/api"
	"github.com/Dado555/glef.icu/user-service/models"
	routes2 "github.com/Dado555/glef.icu/user-service/routes"
	_ "github.com/lib/pq"
	"github.com/rs/cors"
	"net/http"
)

func main() {
	connStr := "host=localhost port=5432 user=postgres dbname=userService password=root"

	corsHandler := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		AllowedMethods: []string{http.MethodPost, http.MethodConnect, http.MethodGet, http.MethodDelete,
			http.MethodHead, http.MethodOptions, http.MethodPut, http.MethodTrace},
		AllowedHeaders:   []string{"*"},
		AllowCredentials: false,
	})

	db := models.NewPostgresDatabase(connStr)
	api := api2.CreateAPI(db)
	routes := routes2.CreateRoutes(api)

	handler := corsHandler.Handler(routes)
	err := http.ListenAndServe(":3000", handler)
	if err != nil {
		panic(err)
		return
	}

	//n := negroni.Classic()
	//n.UseHandler(corsHandler.Handler(routes))
	////n.UseHandler(routes)
	//n.Run("localhost:3000")
}
