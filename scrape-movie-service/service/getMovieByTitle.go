package service

import (
	"encoding/json"
	"fmt"
	"github.com/Dado555/glef.icu/scrape-movie-service/models"
	"io"
	"net/http"
	"regexp"
)

var MovieVar = models.Movie{
	Response:   "",
	Type:       "",
	Title:      "",
	Year:       "",
	Rated:      "",
	Released:   "",
	Runtime:    "",
	Genre:      "",
	Director:   "",
	Writer:     "",
	Actors:     "",
	Plot:       "",
	Language:   "",
	Country:    "",
	ImdbRating: "",
	Metascore:  "",
	ImdbVotes:  "",
	ImdbID:     "",
	Error:      "",
}

func FindByTitle(movieName string, movieYear string) models.Movie {
	var req string

	regex, _ := regexp.Compile(" ")
	regex = regexp.MustCompile(" ")
	movieName = regex.ReplaceAllString(movieName, "+")

	if movieName == "movieName" {
		fmt.Printf("Please enter movie name")
	} else if movieYear == "0000" { // apikey=314a1e4f
		req = "http://www.omdbapi.com/?apikey=314a1e4f&t=" + movieName + "&type=movie&plot=full&r=json"
	} else {
		req = "http://www.omdbapi.com/?apikey=314a1e4f&t=" + movieName + "&y=" + movieYear + "&type=movie&plot=full&r=json"
	}

	r, errGet := http.Get(req)
	if errGet != nil { // apikey=8968bc83
		if movieYear == "0000" {
			req = "http://www.omdbapi.com/?apikey=8968bc83&t=" + movieName + "&type=movie&plot=full&r=json"
		} else {
			req = "http://www.omdbapi.com/?apikey=8968bc83&t=" + movieName + "&y=" + movieYear + "&type=movie&plot=full&r=json"
		}
	}

	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			fmt.Printf("Error during closing body")
		}
	}(r.Body)

	dec := json.NewDecoder(r.Body)
	err := dec.Decode(&MovieVar)
	if err != nil {
		fmt.Printf("Error during json decoding Movie struct")
	}

	fmt.Printf("\n")

	if MovieVar.Response == "True" {
		fmt.Printf("%s (%s)\n", MovieVar.Title, MovieVar.Year)
		fmt.Printf("%s | %s | %s | %s (%s)\n", MovieVar.Rated, MovieVar.Runtime, MovieVar.Genre, MovieVar.Released, MovieVar.Country)
		fmt.Printf("Ratings: %s/10 from %s votes. \t Metascore: %s/100\n", MovieVar.ImdbRating, MovieVar.ImdbVotes, MovieVar.Metascore)
		fmt.Printf("\n%s\n", MovieVar.Plot)
		fmt.Printf("\nDirector: %s\n", MovieVar.Director)
		fmt.Printf("Writer: %s\n", MovieVar.Writer)
		fmt.Printf("Stars: %s\n", MovieVar.Actors)
		fmt.Printf("\nImdb Page: http://www.imdb.com/title/%s\n", MovieVar.ImdbID)
	} else {
		fmt.Printf("%s\n", MovieVar.Error)
	}

	fmt.Printf("\n")

	return MovieVar
}
