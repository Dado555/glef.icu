package user_service

import (
	api2 "github.com/Dado555/glef.icu/user-service/api"
	"github.com/Dado555/glef.icu/user-service/models"
	routes2 "github.com/Dado555/glef.icu/user-service/routes"
	"github.com/urfave/negroni"
)

func main() {
	db := models.NewPostgresDatabase("userService.db")
	api := api2.CreateAPI(db)
	routes := routes2.CreateRoutes(api)
	n := negroni.Classic()
	n.UseHandler(routes)
	n.Run(":3000")
}
