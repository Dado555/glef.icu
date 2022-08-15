package main

import (
	"fmt"
	"github.com/Dado555/glef.icu/scrape-movie-service/service"
)

func main() {
	movie := service.FindByTitle("Harry Potter", "0000")
	fmt.Println(movie)
}
