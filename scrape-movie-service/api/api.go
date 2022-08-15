package api

import (
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"github.com/Dado555/glef.icu/scrape-movie-service/repository"
)

type API struct {
	movies *repository.MovieManager
}

func CreateAPI(db *models.DB) *API {
	movieMgr, _ := repository.NewMovieManager(db)

	return &API{
		movies: movieMgr,
	}
}
