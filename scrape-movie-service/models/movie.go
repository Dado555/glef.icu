package models

import (
	"github.com/jinzhu/gorm"
)

type Movie struct {
	Response   string
	Type       string
	Title      string
	Year       string
	Rated      string
	Released   string
	Runtime    string
	Genre      string
	Director   string
	Writer     string
	Actors     string
	Plot       string
	Language   string
	Country    string
	Poster     string
	ImdbRating string
	Metascore  string
	ImdbVotes  string
	ImdbID     string
	Error      string
}

type MoviePage struct {
	Movies []MovieDb `json:"movies"`
	// Page
	// SortType
	// SortDirection
	// ...
}

type MovieDb struct {
	gorm.Model   `json:"-"`
	Type         string `gorm:"not null;" json:"type"`
	Title        string `gorm:"not null;" json:"title"`
	Year         string `gorm:"not null;" json:"year"`
	Rated        string `gorm:"not null;" json:"rated"`
	Released     string `gorm:"not null;" json:"released"`
	Runtime      string `gorm:"not null;" json:"runtime"`
	Genre        string `gorm:"not null;" json:"genre"`
	Director     string `gorm:"not null;" json:"director"`
	Writer       string `gorm:"not null;" json:"writer"`
	Actors       string `gorm:"not null;" json:"actors"`
	Plot         string `gorm:"not null;" json:"plot"`
	Language     string `gorm:"not null;" json:"language"`
	Country      string `gorm:"not null;" json:"country"`
	Poster       string `gorm:"not null;" json:"poster"`
	ImdbRating   string `gorm:"not null;" json:"imdbRating"`
	Metascore    string `gorm:"not null;" json:"metascore"`
	ImdbVotes    string `gorm:"not null;" json:"imdbVotes"`
	ImdbID       string `gorm:"not null;" json:"imdbID"`
	TorrentLinks string `json:"torrentLinks"`
	TitleLinks   string `json:"titleLinks"`
}

type MovieUpdateDTO struct {
	ImdbLink     string `json:"imdbLink"`
	RottenLink   string `json:"rottenLink"`
	TorrentLinks string `json:"torrentLinks"`
	TitleLinks   string `json:"titleLinks"`
}

func UpdateMovieData(original *MovieDb, new *Movie) {
	original.Title = new.Title
	original.ImdbID = new.ImdbID
	original.Language = new.Language
	original.ImdbVotes = new.ImdbVotes
	original.Metascore = new.Metascore
	original.ImdbRating = new.ImdbRating
	original.Poster = new.Poster
	original.Country = new.Country
	original.Plot = new.Plot
	original.Actors = new.Actors
	original.Writer = new.Writer
	original.Director = new.Director
	original.Genre = new.Genre
	original.Runtime = new.Runtime
	original.Released = new.Released
	original.Rated = new.Rated
	original.Year = new.Year
	original.Type = new.Type
}
