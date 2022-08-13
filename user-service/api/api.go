package api

import "github.com/Dado555/glef.icu/user-service/models"

type API struct {
	users *models.UserManager
}

func CreateAPI(db *models.DB) *API {
	userMgr, _ := models.NewUserManager(db)

	return &API{
		users: userMgr,
	}
}
