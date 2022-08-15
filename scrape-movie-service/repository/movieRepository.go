package repository

import (
	"context"
	"fmt"
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"github.com/deflix-tv/imdb2torrent"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/oz/osdb"
	"go.uber.org/zap"
	"strings"
	"time"
)

type MovieManager struct {
	db *models.DB
}

// NewMovieManager - MovieManager is for managing movies
func NewMovieManager(db *models.DB) (*MovieManager, error) {
	db.AutoMigrate(&models.MovieDb{})
	movieMgr := MovieManager{}
	movieMgr.db = db

	return &movieMgr, nil
}

// AddMovie - Creates a movie
func (state *MovieManager) AddMovie(movie models.Movie) *models.MovieDb {
	// Create new client
	yts := imdb2torrent.NewYTSclient(imdb2torrent.DefaultYTSclientOpts, imdb2torrent.NewInMemoryCache(), zap.NewNop(), false)

	var torrentLinks string
	// Fetch torrents for a movie by imdbID
	torrents, err := yts.FindMovie(context.Background(), movie.ImdbID)
	if err != nil {
		fmt.Println("Couldn't find torrents..")
	} else {
		// Iterate through results and print their magnet URLs
		for _, torrent := range torrents {
			fmt.Println()
			fmt.Println(torrent)
			fmt.Printf("Found torrent: %v\n\n", torrent.MagnetURL)
			if strings.Contains(torrent.Quality, "720p") || strings.Contains(torrent.Quality, "1080p") {
				torrentLinks = torrent.MagnetURL
				fmt.Printf("\nSaved torrent: %v\n\n", torrent.MagnetURL)
			}
		}
	}

	c, err := osdb.NewClient()
	if err != nil {
		fmt.Println("Couldn't find subtitles..")
	}

	// Anonymous login will set c.Token when successful
	if err = c.LogIn("Dado1856", "Teslatesla#555", "eng"); err != nil {
		fmt.Println("Logged in as user..")
	}

	languages := []string{"srp", "hrv", "bos"}
	ids := []string{movie.ImdbID[2:]}
	if movie.ImdbID[2] == '0' {
		ids = append(ids, movie.ImdbID[3:])
	}

	time.Sleep(1 * time.Second)
	var subtitles string
	res, err := c.IMDBSearchByID(ids, languages)
	if err != nil {
		fmt.Println("Couldn't find subtitles..")
	} else {
		best := res.Best()
		subtitles = best.ZipDownloadLink
		//fmt.Println(res)
		//for _, sub := range res {
		//	fmt.Printf("Found %s subtitles file \"%s\" at %s\n",
		//		sub.LanguageName, sub.SubFileName, sub.ZipDownloadLink)
		//	subtitles += sub.ZipDownloadLink + ","
		//}
		//fmt.Println(subtitles)
	}

	// etc.

	movieCreate := &models.MovieDb{
		Type:         movie.Type,
		Title:        movie.Title,
		Year:         movie.Year,
		Rated:        movie.Rated,
		Released:     movie.Released,
		Runtime:      movie.Runtime,
		Genre:        movie.Genre,
		Director:     movie.Director,
		Writer:       movie.Writer,
		Actors:       movie.Actors,
		Plot:         movie.Plot,
		Language:     movie.Language,
		Country:      movie.Country,
		Poster:       movie.Poster,
		ImdbRating:   movie.ImdbRating,
		Metascore:    movie.Metascore,
		ImdbVotes:    movie.ImdbVotes,
		ImdbID:       movie.ImdbID,
		TorrentLinks: torrentLinks,
		TitleLinks:   subtitles,
	}
	state.db.Create(&movieCreate)
	return movieCreate
}

// FindMovie - return movie by title
func (state *MovieManager) FindMovie(title string) *models.MovieDb {
	movieFind := models.MovieDb{}
	state.db.Where("title=?", title).Find(&movieFind)
	return &movieFind
}

// FindMovieByImdbID - return movie by imdbID
func (state *MovieManager) FindMovieByImdbID(imdbId string) *models.MovieDb {
	movieFindID := models.MovieDb{}
	state.db.Where("imdb_id=?", imdbId).Find(&movieFindID)
	return &movieFindID
}

// GetMovieDownloadLink - return movie download links by imdbID
func (state *MovieManager) GetMovieDownloadLink(imdbId string) *string {
	movieFindID := models.MovieDb{}
	state.db.Where("imdb_id=?", imdbId).Find(&movieFindID)
	return &movieFindID.TorrentLinks
}

// GetMovieTitleLinks - return movie title links by imdbID
func (state *MovieManager) GetMovieTitleLinks(imdbId string) *string {
	movieFindID := models.MovieDb{}
	state.db.Where("imdb_id=?", imdbId).Find(&movieFindID)
	return &movieFindID.TitleLinks
}

// HasMovie - Movie with this imdbID exists?
func (state *MovieManager) HasMovie(imdbID string) bool {
	if err := state.db.Where("imdb_id=?", imdbID).Find(&models.MovieDb{}).Error; err != nil {
		return false
	}
	return true
}

func (state *MovieManager) GetAllMovies() []models.MovieDb {
	var movies []models.MovieDb
	state.db.Find(&movies)
	return movies
}

func (state *MovieManager) GetMovies(page uint64, size uint64) []models.MovieDb {
	var movies []models.MovieDb
	state.db.Find(&movies).Offset(int(page * size)).Limit(int(size))
	return movies
}
