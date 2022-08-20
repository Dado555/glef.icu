package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type WishlistItem struct {
	gorm.Model `json:"-"`
	UserId     uint   `json:"userId"`
	Title      string `gorm:"not null;" json:"title"`
	Genre      string `gorm:"not null;" json:"genre"`
	ImdbID     string `gorm:"not null;" json:"imdbID"`
	Poster     string `gorm:"not null;" json:"poster"`
	ImdbRating string `gorm:"not null;" json:"imdbRating"`
	Released   string `gorm:"not null;" json:"released"`
}

type WishlistPage struct {
	Wishlist []WishlistItem `json:"wishlist"`
}

type WishlistItemJSON struct {
	UserId     uint   `json:"userId"`
	Title      string `gorm:"not null;" json:"title"`
	Genre      string `gorm:"not null;" json:"genre"`
	ImdbID     string `gorm:"not null;" json:"imdbID"`
	Poster     string `gorm:"not null;" json:"poster"`
	ImdbRating string `gorm:"not null;" json:"imdbRating"`
	Released   string `gorm:"not null;" json:"released"`
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
	state.db.Where("user_id=?", userId).Find(&wishlist).Offset(int(page * size)).Limit(int(size))
	return &wishlist
}

func (state *WishlistManager) FindWishlistItem(userId uint, movieId string) *WishlistItem {
	wishlistItem := WishlistItem{}
	state.db.Where("imdb_id=? AND user_id=?", movieId, userId).Find(&wishlistItem)
	return &wishlistItem
}

func (state *WishlistManager) SaveWishlistItem(watchlistItem *WishlistItemJSON) *WishlistItem {
	wishlistItemCreate := WishlistItem{
		UserId:     watchlistItem.UserId,
		Title:      watchlistItem.Title,
		Genre:      watchlistItem.Genre,
		ImdbID:     watchlistItem.ImdbID,
		Poster:     watchlistItem.Poster,
		ImdbRating: watchlistItem.ImdbRating,
		Released:   watchlistItem.Released,
	}
	state.db.Create(&wishlistItemCreate)
	return &wishlistItemCreate
}

func (state *WishlistManager) DeleteWishlistItem(wishlistItem *WishlistItem) {
	state.db.Unscoped().Delete(wishlistItem)
}
