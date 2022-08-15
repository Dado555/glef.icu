package main

import (
	routes2 "github.com/Dado555/glef.icu/scrape-movie-service/routes"
	"github.com/rs/cors"
	"net/http"
)

func main() {
	corsHandler := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		AllowedMethods: []string{http.MethodPost, http.MethodConnect, http.MethodGet, http.MethodDelete,
			http.MethodHead, http.MethodOptions, http.MethodPut, http.MethodTrace},
		AllowedHeaders:   []string{"*"},
		AllowCredentials: false,
	})

	routes := routes2.CreateRoutes()
	handler := corsHandler.Handler(routes)
	err := http.ListenAndServe(":3001", handler)
	if err != nil {
		panic(err)
		return
	}
}
