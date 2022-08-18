package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type WishlistItem struct {
	gorm.Model `json:"-"`
	MovieId    uint `json:"movieId"`
	UserId     uint `json:"userId"`
}

type WishlistPage struct {
	Wishlist *[]WishlistItem `json:"wishlist"`
}

type WishlistItemJSON struct {
	MovieId uint `json:"movieId"`
	UserId  uint `json:"userId"`
}

type WishlistManager struct {
	db *DB
}

// NewWishlistManager - WatchlistManager is for managing wishlist
func NewWishlistManager(db *DB) (*WishlistManager, error) {
	db.AutoMigrate(&WishlistItem{})
	wlMgr := WishlistManager{}
	wlMgr.db = db

	return &wlMgr, nil
}

func (state *WishlistManager) GetWishlist(page uint64, size uint64, userId uint) *[]WishlistItem {
	var wishlist []WishlistItem
	state.db.Where("user_id=? AND deleted_at=?", userId, nil).Find(&wishlist).Offset(int(page * size)).Limit(int(size))
	return &wishlist
}

func (state *WishlistManager) FindWishlistItem(userId uint, movieId uint) *WishlistItem {
	wishlistItem := WishlistItem{}
	state.db.Where("movie_id=? AND user_id=? AND deleted_at=?", movieId, userId, nil).Find(&wishlistItem)
	return &wishlistItem
}

func (state *WishlistManager) SaveWishlistItem(watchlistItem *WishlistItemJSON) *WishlistItem {
	wishlistItemCreate := WishlistItem{
		MovieId: watchlistItem.MovieId,
		UserId:  watchlistItem.UserId,
	}
	state.db.Create(&wishlistItemCreate)
	return &wishlistItemCreate
}

func (state *WishlistManager) DeleteWishlistItem(wishlistItem *WishlistItem) {
	state.db.Delete(wishlistItem)
}
