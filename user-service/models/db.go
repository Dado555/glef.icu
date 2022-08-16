package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"golang.org/x/crypto/bcrypt"
)

type DB struct {
	*gorm.DB
}

func NewPostgresDatabase(dataSource string) *DB {
	db, err := gorm.Open("postgres", dataSource)
	if err != nil {
		panic(err)
	}
	if err = db.DB().Ping(); err != nil {
		panic(err)
	}

	return &DB{db}
}

func HashPassword(password string) string {
	hash, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		panic("Could not hash the password!")
	}
	return string(hash)
}
