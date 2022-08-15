package api

import (
	"encoding/json"
	"github.com/Dado555/glef.icu/user-service/auth"
	"github.com/Dado555/glef.icu/user-service/models"
	"net/http"
)

type UserJSON struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

func (api *API) UserLogin(w http.ResponseWriter, req *http.Request) {

	decoder := json.NewDecoder(req.Body)
	jsonData := UserJSON{}
	err := decoder.Decode(&jsonData)

	if err != nil || jsonData.Username == "" || jsonData.Password == "" {
		http.Error(w, "Wrong username or password", http.StatusBadRequest)
		return
	}

	user := api.users.FindUser(jsonData.Username)
	if user.Username == "" {
		http.Error(w, "Username not found", http.StatusBadRequest)
		return
	}

	if !api.users.CheckPassword(user.Password, jsonData.Password) {
		http.Error(w, "Wrong password", http.StatusBadRequest)
		return
	}

	jsonToken := auth.GetJSONToken(user)

	w.Header().Set("Content-Type", "application/json")
	_, err = w.Write([]byte(jsonToken))
	if err != nil {
		http.Error(w, "Could not get token", http.StatusBadRequest)
		return
	}
}

//func enableCors(w *http.ResponseWriter) {
//	(*w).Header().Set("Access-Control-Allow-Origin", "*")
//}

func (api *API) UserSignup(w http.ResponseWriter, req *http.Request) {
	//enableCors(&w)

	decoder := json.NewDecoder(req.Body)
	jsonData := UserJSON{}
	err := decoder.Decode(&jsonData)

	if err != nil || jsonData.Username == "" || jsonData.Password == "" {
		http.Error(w, "Missing username or password", http.StatusBadRequest)
		return
	}

	if api.users.HasUser(jsonData.Username) {
		http.Error(w, "username already exists", http.StatusBadRequest)
		return
	}

	user := api.users.AddUser(jsonData.Username, jsonData.Password)

	jsonToken := auth.GetJSONToken(user)

	w.Header().Set("Content-Type", "application/json")
	_, err = w.Write([]byte(jsonToken))
	if err != nil {
		http.Error(w, "Could not get token", http.StatusBadRequest)
		return
	}
}

func (api *API) GetUserFromContext(req *http.Request) *models.User {
	userClaims := auth.GetUserClaimsFromContext(req)
	user := api.users.FindUserByUUID(userClaims["uuid"].(string))
	return user
}

func (api *API) UserInfo(w http.ResponseWriter, req *http.Request) {

	user := api.GetUserFromContext(req)
	js, _ := json.Marshal(user)
	w.Header().Set("Content-Type", "application/json")
	_, err := w.Write(js)
	if err != nil {
		http.Error(w, "Could not get user info", http.StatusBadRequest)
		return
	}
}