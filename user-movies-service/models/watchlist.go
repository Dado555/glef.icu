package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type WatchlistItem struct {
	gorm.Model `json:"-"`
	MovieId    uint `json:"movieId"`
	UserId     uint `json:"userId"`
}

type WatchlistPage struct {
	Watchlist *[]WatchlistItem `json:"watchlist"`
}

type WatchlistItemJSON struct {
	MovieId uint `json:"movieId"`
	UserId  uint `json:"userId"`
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
	state.db.Where("user_id=? AND deleted_at=?", userId, nil).Find(&watchlist).Offset(int(page * size)).Limit(int(size))
	return &watchlist
}

func (state *WatchlistManager) FindWatchlistItem(userId uint, movieId uint) *WatchlistItem {
	watchlistItem := WatchlistItem{}
	state.db.Where("movie_id=? AND user_id=? AND deleted_at=?", movieId, userId, nil).Find(&watchlistItem)
	return &watchlistItem
}

func (state *WatchlistManager) SaveWatchlistItem(watchlistItem *WatchlistItemJSON) *WatchlistItem {
	watchlistItemCreate := WatchlistItem{
		MovieId: watchlistItem.MovieId,
		UserId:  watchlistItem.UserId,
	}
	state.db.Create(&watchlistItemCreate)
	return &watchlistItemCreate
}

func (state *WatchlistManager) DeleteWatchlistItem(watchlistItem *WatchlistItem) {
	state.db.Delete(watchlistItem)
}
