package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

type Role struct {
	gorm.Model `json:"-"`
	Name       string `gorm:"not null;unique" json:"role"`
}

type RoleManager struct {
	db *DB
}

// NewRoleManager - new RoleManager for managing roles
func NewRoleManager(db *DB) (*RoleManager, error) {
	db.AutoMigrate(&Role{})
	roleMgr := RoleManager{}
	roleMgr.db = db

	return &roleMgr, nil
}

func (state *RoleManager) FindRoleById(id uint) (*Role, error) {
	var role Role
	state.db.Where("id=?", id).Find(&role)
	return &role, nil
}

func (state *RoleManager) InitDatabase() {
	roles := []Role{
		{Name: "ADMIN"},
		{Name: "USER"},
	}

	for _, role := range roles {
		state.db.Create(&role)
	}
}
