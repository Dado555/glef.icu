package models

import (
	"github.com/jinzhu/gorm"
	// postgres db driver
	_ "github.com/jinzhu/gorm/dialects/postgres"
	uuid "github.com/satori/go.uuid"
	"golang.org/x/crypto/bcrypt"
)

type User struct {
	gorm.Model `json:"-"`
	Username   string `gorm:"not null;unique" json:"username"`
	Password   string `gorm:"not null" json:"-"`
	UUID       string `gorm:"not null;unique" json:"uuid"`
}

type UserManager struct {
	db *DB
}

// NewUserManager - new UserManager for managing users
func NewUserManager(db *DB) (*UserManager, error) {
	db.AutoMigrate(&User{})
	userMgr := UserManager{}
	userMgr.db = db

	return &userMgr, nil
}

// AddUser - Creates a user and hashes the password
func (state *UserManager) AddUser(username, password string) *User {
	passwordHash := state.HashPassword(password)
	guid := uuid.NewV4()
	user := &User{
		Username: username,
		Password: passwordHash,
		UUID:     guid.String(),
	}
	state.db.Create(&user)
	return user
}

// FindUser - return User by username
func (state *UserManager) FindUser(username string) *User {
	user := User{}
	state.db.Where("username=?", username).Find(&user)
	return &user
}

// FindUserByUUID - return User by uuid
func (state *UserManager) FindUserByUUID(uuid string) *User {
	user := User{}
	state.db.Where("uuid=?", uuid).Find(&user)
	return &user
}

// HasUser - User with this username exists?
func (state *UserManager) HasUser(username string) bool {
	if err := state.db.Where("username=?", username).Find(&User{}).Error; err != nil {
		return false
	}
	return true
}

// HashPassword - Hash the password
func (state *UserManager) HashPassword(password string) string {
	hash, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		panic("Could not hash the password!")
	}
	return string(hash)
}

// CheckPassword - compare a hashed password with a given text
func (state *UserManager) CheckPassword(hashedPassword, password string) bool {
	if bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password)) != nil {
		return false
	}
	return true
}
