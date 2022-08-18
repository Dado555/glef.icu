package api

import (
	"encoding/json"
	"fmt"
	"github.com/Dado555/glef.icu/user-movies-service/models"
	"net/http"
	"strconv"
)

func (api *API) GetWishlist(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	page := queryParams.Get("page")
	size := queryParams.Get("size")
	userId := queryParams.Get("userId")

	pageParsed, _ := strconv.ParseUint(page, 10, 64)
	sizeParsed, _ := strconv.ParseUint(size, 10, 64)
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	wishlist := api.wishlist.GetWishlist(pageParsed, sizeParsed, uint(userIdParsed))

	wishlistPage := models.WishlistPage{
		Wishlist: wishlist,
	}

	err := json.NewEncoder(w).Encode(wishlistPage)
	if err != nil {
		http.Error(w, "Could not return wishlist", http.StatusBadRequest)
		return
	}
}

func (api *API) GetWishlistItem(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	userId := queryParams.Get("userId")
	movieId := queryParams.Get("movieId")
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	wishlistItem := api.wishlist.FindWishlistItem(uint(userIdParsed), movieId)

	err := json.NewEncoder(w).Encode(wishlistItem)
	if err != nil {
		http.Error(w, "Could not return wishlistItem", http.StatusBadRequest)
		return
	}
}

func (api *API) SaveWishlistItem(w http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	jsonData := models.WishlistItemJSON{}
	err := decoder.Decode(&jsonData)

	if err != nil || len(jsonData.ImdbID) < 6 || jsonData.UserId <= 0 {
		http.Error(w, "Missing MovieId or UserId", http.StatusBadRequest)
		return
	}

	wishlistItem := api.wishlist.FindWishlistItem(jsonData.UserId, jsonData.ImdbID)
	if wishlistItem.UserId > 0 {
		http.Error(w, "Wishlist item for this user and movie already exists", http.StatusBadRequest)
		return
	}

	newItem := api.wishlist.SaveWishlistItem(&jsonData)

	w.Header().Set("Content-Type", "application/json")
	err = json.NewEncoder(w).Encode(newItem)
	if err != nil {
		http.Error(w, "Could not save wishlist item", http.StatusBadRequest)
		return
	}
}

func (api *API) DeleteWishlistItem(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)

	userId := queryParams.Get("userId")
	movieId := queryParams.Get("movieId")
	userIdParsed, _ := strconv.ParseUint(userId, 10, 64)

	wishlistItem := api.wishlist.FindWishlistItem(uint(userIdParsed), movieId)
	api.wishlist.DeleteWishlistItem(wishlistItem)
}
