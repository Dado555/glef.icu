package api

import (
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
)

type API struct {
	movies *models.MovieManager
}

func CreateAPI(db *models.DB) *API {
	movieMgr, _ := models.NewMovieManager(db)

	return &API{
		movies: movieMgr,
	}
}
