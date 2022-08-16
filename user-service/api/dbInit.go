package api

func (api *API) DbInit() {
	api.roles.InitDatabase()
	api.users.InitDatabase()
}
