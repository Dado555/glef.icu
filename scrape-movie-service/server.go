package main

import (
	api2 "github.com/Dado555/glef.icu/scrape-movie-service/api"
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	routes2 "github.com/Dado555/glef.icu/scrape-movie-service/routes"
	"github.com/rs/cors"
	"net/http"
)

func main() {
	connStr := "host=localhost port=5432 user=postgres dbname=movieService password=root"

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
	err := http.ListenAndServe(":3001", handler)
	if err != nil {
		panic(err)
		return
	}
}
