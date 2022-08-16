package api

import "github.com/Dado555/glef.icu/user-service/models"

type API struct {
	users *models.UserManager
	roles *models.RoleManager
}

func CreateAPI(db *models.DB) *API {
	userMgr, _ := models.NewUserManager(db)
	roleMgr, _ := models.NewRoleManager(db)

	return &API{
		users: userMgr,
		roles: roleMgr,
	}
}
