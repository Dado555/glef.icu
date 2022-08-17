package models

import (
	"github.com/golang-jwt/jwt"
	"github.com/jinzhu/gorm"
	// postgres db driver
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"golang.org/x/crypto/bcrypt"
)

type User struct {
	gorm.Model    `json:"-"`
	Username      string `gorm:"not null;unique" json:"username"`
	Password      string `gorm:"not null" json:"-"`
	RoleID        uint   `gorm:"not null" json:"roleId"`
	Banned        bool   `gorm:"not null" json:"banned"`
	CanBeBanned   bool   `gorm:"not null" json:"canBeBanned"`
	Gender        string `json:"gender"`
	Age           uint   `json:"age"`
	FavouriteTags string `json:"favouriteTags"`
}

type JwtClaims struct {
	jwt.StandardClaims
	Authority string `json:"authority"`
	Username  string `json:"username"`
}

type UserDTO struct {
	ID            uint   `json:"id"`
	Username      string `json:"username"`
	RoleId        uint   `json:"role"`
	Banned        bool   `json:"banned"`
	CanBeBanned   bool   `json:"canBeBanned"`
	Gender        string `json:"gender"`
	Age           uint   `json:"age"`
	FavouriteTags string `json:"favouriteTags"`
}

type UsersPage struct {
	Users *[]UserDTO `json:"users"`
	// Page
	// SortType
	// SortDirection
	// ...
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
		Username:    username,
		Password:    passwordHash,
		RoleID:      2,
		Banned:      false,
		CanBeBanned: false,
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
func (state *UserManager) FindUserByID(id uint64) *User {
	user := User{}
	state.db.Where("id=?", id).Find(&user)
	return &user
}

// FindUserDTO - return UserDTO by username
func (state *UserManager) FindUserDTO(username string) *UserDTO {
	user := User{}
	state.db.Where("username=?", username).Find(&user)
	return UserToUserDTO(&user)
}

// FindUserByIDDTO - return UserDTO by id
func (state *UserManager) FindUserByIDDTO(id uint64) *UserDTO {
	user := User{}
	state.db.Where("id=?", id).Find(&user)
	return UserToUserDTO(&user)
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

func (state *UserManager) GetAllUsers() (*[]UserDTO, error) {
	var users []User
	dbRes := state.db.Find(&users)
	return UserListToUserDTOList(&users), dbRes.Error
}

func UserToUserDTO(user *User) *UserDTO {
	return &UserDTO{ID: user.ID, Username: user.Username, RoleId: user.RoleID, Banned: user.Banned,
		CanBeBanned: user.CanBeBanned, Gender: user.Gender, Age: user.Age, FavouriteTags: user.FavouriteTags}
}

func UserListToUserDTOList(users *[]User) *[]UserDTO {
	var userDTOs []UserDTO
	for _, us := range *users {
		userDTOs = append(userDTOs, *UserToUserDTO(&us))
	}
	return &userDTOs
}

func (state *UserManager) InitDatabase() {
	users := []User{
		{
			Username: "admin", Password: HashPassword("admin"), RoleID: 1, Banned: false, CanBeBanned: false,
		},
		{
			Username: "user", Password: HashPassword("user"), RoleID: 2, Banned: false, CanBeBanned: false,
			Gender: "male",
		},
	}

	for _, user := range users {
		state.db.Create(&user)
	}
}

func (state *UserManager) SearchUsers(name string) *[]UserDTO {
	var users []User
	state.db.Where("LOWER(username) LIKE ?", "%"+name+"%").Find(&users)
	return UserListToUserDTOList(&users)
}

func (state *UserManager) GetUsers(page uint64, size uint64) *[]UserDTO {
	var users []User
	state.db.Find(&users).Offset(int(page * size)).Limit(int(size))
	return UserListToUserDTOList(&users)
}
