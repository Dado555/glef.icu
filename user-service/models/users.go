package models

import (
	"github.com/golang-jwt/jwt"
	"github.com/jinzhu/gorm"
	// postgres db driver
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"golang.org/x/crypto/bcrypt"
)

type User struct {
	gorm.Model `json:"-"`
	Username   string `gorm:"not null;unique" json:"username"`
	Password   string `gorm:"not null" json:"-"`
	RoleID     uint   `gorm:"not null" json:"roleId"`
	Banned     bool   `gorm:"not null" json:"banned"`
}

type JwtClaims struct {
	jwt.StandardClaims
	Authority string `json:"authority"`
	Username  string `json:"username"`
}

type UserDTO struct {
	ID       uint   `json:"id"`
	Username string `json:"username"`
	RoleId   uint   `json:"role"`
	Banned   bool   `json:"banned"`
}

type UserJWTExtract struct {
	Username string `json:"username"`
	Role     string `json:"role"`
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
	user := &User{
		Username: username,
		Password: passwordHash,
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

// FindUserByID - return User by id
func (state *UserManager) FindUserByID(id uint) *User {
	user := User{}
	state.db.Where("id=?", id).Find(&user)
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

func (state *UserManager) UpdateUser(user *User) {
	state.db.Save(user)
}

func (state *UserManager) GetAllUsers() ([]User, error) {
	var users []User
	dbRes := state.db.Find(&users)
	return users, dbRes.Error
}

func UserToUserDTO(user *User) UserDTO {
	return UserDTO{ID: user.ID, Username: user.Username, RoleId: user.RoleID, Banned: user.Banned}
}

func (state *UserManager) InitDatabase() {
	users := []User{
		{
			Username: "admin", Password: HashPassword("admin"), RoleID: 1, Banned: false,
		},
		{
			Username: "user", Password: HashPassword("user"), RoleID: 2, Banned: false,
		},
	}

	for _, user := range users {
		state.db.Create(&user)
	}
}
