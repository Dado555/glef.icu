package api

import "github.com/Dado555/glef.icu/user-movies-service/models"

type API struct {
	wishlist  *models.WishlistManager
	watchlist *models.WatchlistManager
}

func CreateAPI(db *models.DB) *API {
	watchlistMgr, _ := models.NewWatchlistManager(db)
	wishlistMgr, _ := models.NewWishlistManager(db)

	return &API{
		wishlist:  wishlistMgr,
		watchlist: watchlistMgr,
	}
}
