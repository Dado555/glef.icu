package api

import (
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"github.com/Dado555/glef.icu/scrape-movie-service/repository"
)

type API struct {
	movies *repository.MovieManager
	tags   *models.TagManager
}

func CreateAPI(db *models.DB) *API {
	movieMgr, _ := repository.NewMovieManager(db)
	tagMgr, _ := models.NewTagManager(db)

	return &API{
		movies: movieMgr,
		tags:   tagMgr,
	}
}
