package api

import (
	"encoding/json"
	"fmt"
	"github.com/Dado555/glef.icu/user-movies-service/models"
	"net/http"
	"strconv"
)

func (api *API) GetWatchlist(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	page := queryParams.Get("page")
	size := queryParams.Get("size")
	userId := queryParams.Get("userId")

	pageParsed, _ := strconv.ParseUint(page, 10, 64)
	sizeParsed, _ := strconv.ParseUint(size, 10, 64)
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	watchlist := api.watchlist.GetWatchlist(pageParsed, sizeParsed, uint(userIdParsed))

	watchlistPage := models.WatchlistPage{
		Watchlist: watchlist,
	}

	err := json.NewEncoder(w).Encode(watchlistPage)
	if err != nil {
		http.Error(w, "Could not return watchlist", http.StatusBadRequest)
		return
	}
}

func (api *API) GetWatchlistItem(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	userId := queryParams.Get("userId")
	movieId := queryParams.Get("movieId")
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	watchlistItem := api.watchlist.FindWatchlistItem(uint(userIdParsed), movieId)

	err := json.NewEncoder(w).Encode(watchlistItem)
	if err != nil {
		http.Error(w, "Could not return watchlistItem", http.StatusBadRequest)
		return
	}
}

func (api *API) SaveWatchlistItem(w http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	jsonData := models.WatchlistItemJSON{}
	err := decoder.Decode(&jsonData)

	if err != nil || len(jsonData.ImdbID) < 6 || jsonData.UserId <= 0 {
		http.Error(w, "Missing MovieId or UserId", http.StatusBadRequest)
		return
	}

	watchlistItem := api.watchlist.FindWatchlistItem(jsonData.UserId, jsonData.ImdbID)
	if watchlistItem.UserId > 0 {
		http.Error(w, "Watchlist item for this user and movie already exists", http.StatusBadRequest)
		return
	}

	newItem := api.watchlist.SaveWatchlistItem(&jsonData)

	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(newItem)
	if err != nil {
		http.Error(w, "Could not save watchlist item", http.StatusBadRequest)
		return
	}
}

func (api *API) DeleteWatchlistItem(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	userId := queryParams.Get("userId")
	movieId := queryParams.Get("movieId")
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	watchlistItem := api.watchlist.FindWatchlistItem(uint(userIdParsed), movieId)
	api.watchlist.DeleteWatchlistItem(watchlistItem)
}
