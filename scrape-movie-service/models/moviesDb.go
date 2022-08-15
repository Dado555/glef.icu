package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type MovieDb struct {
	gorm.Model `json:"-"`
	Type       string `gorm:"not null;" json:"type"`
	Title      string `gorm:"not null;" json:"title"`
	Year       string `gorm:"not null;" json:"year"`
	Rated      string `gorm:"not null;" json:"rated"`
	Released   string `gorm:"not null;" json:"released"`
	Runtime    string `gorm:"not null;" json:"runtime"`
	Genre      string `gorm:"not null;" json:"genre"`
	Director   string `gorm:"not null;" json:"director"`
	Writer     string `gorm:"not null;" json:"writer"`
	Actors     string `gorm:"not null;" json:"actors"`
	Plot       string `gorm:"not null;" json:"plot"`
	Language   string `gorm:"not null;" json:"language"`
	Country    string `gorm:"not null;" json:"country"`
	Poster     string `gorm:"not null;" json:"poster"`
	ImdbRating string `gorm:"not null;" json:"imdbRating"`
	Metascore  string `gorm:"not null;" json:"metascore"`
	ImdbVotes  string `gorm:"not null;" json:"imdbVotes"`
	ImdbID     string `gorm:"not null;" json:"imdbID"`
}

type MovieManager struct {
	db *DB
}

// NewMovieManager - MovieManager is for managing movies
func NewMovieManager(db *DB) (*MovieManager, error) {
	db.AutoMigrate(&MovieDb{})
	movieMgr := MovieManager{}
	movieMgr.db = db

	return &movieMgr, nil
}

// AddMovie - Creates a movie
func (state *MovieManager) AddMovie(movie Movie) *MovieDb {
	movieCreate := &MovieDb{
		Type:       movie.Type,
		Title:      movie.Title,
		Year:       movie.Year,
		Rated:      movie.Rated,
		Released:   movie.Released,
		Runtime:    movie.Runtime,
		Genre:      movie.Genre,
		Director:   movie.Director,
		Writer:     movie.Writer,
		Actors:     movie.Actors,
		Plot:       movie.Plot,
		Language:   movie.Language,
		Country:    movie.Country,
		Poster:     movie.Poster,
		ImdbRating: movie.ImdbRating,
		Metascore:  movie.Metascore,
		ImdbVotes:  movie.ImdbVotes,
		ImdbID:     movie.ImdbID,
	}
	state.db.Create(&movieCreate)
	return movieCreate
}

// FindMovie - return movie by title
func (state *MovieManager) FindMovie(title string) *MovieDb {
	movieFind := MovieDb{}
	state.db.Where("title=?", title).Find(&movieFind)
	return &movieFind
}

// FindMovieByImdbID - return movie by imdbID
func (state *MovieManager) FindMovieByImdbID(uuid string) *MovieDb {
	movieFindID := MovieDb{}
	state.db.Where("imdb_id=?", uuid).Find(&movieFindID)
	return &movieFindID
}

// HasMovie - Movie with this imdbID exists?
func (state *MovieManager) HasMovie(imdbID string) bool {
	if err := state.db.Where("imdb_id=?", imdbID).Find(&MovieDb{}).Error; err != nil {
		return false
	}
	return true
}
