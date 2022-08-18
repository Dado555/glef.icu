package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type WatchlistItem struct {
	gorm.Model `json:"-"`
	UserId     uint   `json:"userId"`
	Title      string `gorm:"not null;" json:"title"`
	Genre      string `gorm:"not null;" json:"genre"`
	ImdbID     string `gorm:"not null;" json:"imdbID"`
	Poster     string `gorm:"not null;" json:"poster"`
	ImdbRating string `gorm:"not null;" json:"imdbRating"`
	Released   string `gorm:"not null;" json:"released"`
}

type WatchlistPage struct {
	Watchlist []WatchlistItem `json:"watchlist"`
}

type WatchlistItemJSON struct {
	UserId     uint   `json:"userId"`
	Title      string `gorm:"not null;" json:"title"`
	Genre      string `gorm:"not null;" json:"genre"`
	ImdbID     string `gorm:"not null;" json:"imdbID"`
	Poster     string `gorm:"not null;" json:"poster"`
	ImdbRating string `gorm:"not null;" json:"imdbRating"`
	Released   string `gorm:"not null;" json:"released"`
}

type WatchlistManager struct {
	db *DB
}

func NewWatchlistManager(db *DB) (*WatchlistManager, error) {
	db.AutoMigrate(&WatchlistItem{})
	wlMgr := WatchlistManager{}
	wlMgr.db = db

	return &wlMgr, nil
}

func (state *WatchlistManager) GetWatchlist(page uint64, size uint64, userId uint) *[]WatchlistItem {
	var watchlist []WatchlistItem
	state.db.Where("user_id=?", userId).Find(&watchlist).Offset(int(page * size)).Limit(int(size))
	return &watchlist
}

func (state *WatchlistManager) FindWatchlistItem(userId uint, movieId string) *WatchlistItem {
	watchlistItem := WatchlistItem{}
	state.db.Where("imdb_id=? AND user_id=?", movieId, userId).Find(&watchlistItem)
	return &watchlistItem
}

func (state *WatchlistManager) SaveWatchlistItem(watchlistItem *WatchlistItemJSON) *WatchlistItem {
	watchlistItemCreate := WatchlistItem{
		UserId:     watchlistItem.UserId,
		Title:      watchlistItem.Title,
		Genre:      watchlistItem.Genre,
		ImdbID:     watchlistItem.ImdbID,
		Poster:     watchlistItem.Poster,
		ImdbRating: watchlistItem.ImdbRating,
		Released:   watchlistItem.Released,
	}
	state.db.Create(&watchlistItemCreate)
	return &watchlistItemCreate
}

func (state *WatchlistManager) DeleteWatchlistItem(watchlistItem *WatchlistItem) {
	state.db.Delete(watchlistItem)
}
