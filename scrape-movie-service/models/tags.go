package models

import (
	"fmt"
	"github.com/jinzhu/gorm"
	"sort"
)

type Tag struct {
	Id   int    `gorm:"not null" json:"id"`
	Name string `gorm:"not null" json:"name"`
}

type TagDB struct {
	gorm.Model `json:"-"`
	MovieId    uint   `gorm:"not null" json:"movieId"`
	Name       string `gorm:"not null" json:"name"`
	Genre      string `gorm:"not null" json:"genre"`
}

type TagManager struct {
	db *DB
}

// NewTagManager - TagManager is for managing tags
func NewTagManager(db *DB) (*TagManager, error) {
	db.AutoMigrate(&TagDB{})
	tagMgr := TagManager{}
	tagMgr.db = db

	return &tagMgr, nil
}

// FindTag - return tag by name
func (state *TagManager) FindTag(name string) *TagDB {
	tagFind := TagDB{}
	state.db.Where("name=?", name).Find(&tagFind)
	return &tagFind
}

// FindTagId - return tag by id
func (state *TagManager) FindTagId(id uint) *TagDB {
	tagFind := TagDB{}
	state.db.Where("id=?", id).Find(&tagFind)
	return &tagFind
}

func (state *TagManager) GetAllTags() *[]TagDB {
	var tags []TagDB
	state.db.Find(&tags)
	return &tags
}

// AddTag - Creates a tag
func (state *TagManager) AddTag(tag TagDB) {
	state.db.Create(&tag)
}

func (state *TagManager) AggMovieTags(movieId uint, tags *[]Tag, genre string) {
	for _, tag := range *tags {
		state.AddTag(TagDB{MovieId: movieId, Name: tag.Name, Genre: genre})
	}
}

func (state *TagManager) SearchTags(name string) *[]string {
	var tags []TagDB
	state.db.Where("LOWER(name) LIKE ?", "%"+name+"%").Find(&tags).Offset(20).Limit(20)
	var finalTags []string
	for _, tag := range tags {
		sort.Strings(finalTags)
		i := sort.Search(len(finalTags), func(i int) bool { return finalTags[i] >= tag.Name })
		if i < len(finalTags) && finalTags[i] == tag.Name {
			fmt.Printf("found \"%s\" at finalTags[%d]\n", finalTags[i], i)
		} else {
			finalTags = append(finalTags, tag.Name)
		}
	}
	return &finalTags
}
