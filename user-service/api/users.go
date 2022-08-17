package api

import (
	"encoding/json"
	"fmt"
	"github.com/Dado555/glef.icu/user-service/auth"
	"github.com/Dado555/glef.icu/user-service/models"
	"github.com/gorilla/mux"
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

	role, _ := api.roles.FindRoleById(user.RoleID)
	jsonToken := auth.GetJSONToken(user, role.Name)

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

	role, _ := api.roles.FindRoleById(user.RoleID)
	jsonToken := auth.GetJSONToken(user, role.Name)

	w.Header().Set("Content-Type", "application/json")
	_, err = w.Write([]byte(jsonToken))
	if err != nil {
		http.Error(w, "Could not get token", http.StatusBadRequest)
		return
	}
}

func (api *API) GetUserFromContext(req *http.Request) *models.User {
	userClaims := auth.GetUserClaimsFromContext(req)
	user := api.users.FindUserByID(userClaims["id"].(uint))
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

func (api *API) AdminAuthorize(w http.ResponseWriter, req *http.Request) {
	authorize(w, req, "ADMIN")
}

func (api *API) UserAuthorize(w http.ResponseWriter, req *http.Request) {
	authorize(w, req, "USER")
}

func authorize(w http.ResponseWriter, req *http.Request, role string) {
	w.Header().Set("Content-Type", "application/json")
	token, err := auth.ExtractJWT(req)
	if err != nil || !token.Valid {
		w.WriteHeader(http.StatusForbidden)
		return
	}
	if token.Claims.(*models.JwtClaims).Authority != role {
		w.WriteHeader(http.StatusForbidden)
		return
	}
	w.WriteHeader(http.StatusOK)
}

func (api *API) BanUser(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(req)
	username := params["username"]

	user := api.users.FindUser(username)
	if user == nil {
		http.Error(w, "Could not get user", http.StatusBadRequest)
	} else {
		if user.ID == 1 {
			http.Error(w, "Cannot ban admin", http.StatusBadRequest)
		} else {
			user.Banned = true
			api.users.UpdateUser(user)
		}
	}
}

func (api *API) GetUsernameFromJWT(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	token, err := auth.ExtractJWT(req)
	if err != nil || !token.Valid {
		w.WriteHeader(http.StatusForbidden)
		err := json.NewEncoder(w).Encode(models.UserJWTExtract{Username: "", Role: ""})
		if err != nil {
			http.Error(w, "Could not encode user info from token", http.StatusBadRequest)
			return
		}
	} else {
		w.WriteHeader(http.StatusOK)
		username := token.Claims.(*models.JwtClaims).Username
		role := token.Claims.(*models.JwtClaims).Authority
		err := json.NewEncoder(w).Encode(models.UserJWTExtract{Username: username, Role: role})
		if err != nil {
			http.Error(w, "Could not encode user info from token", http.StatusBadRequest)
			return
		}
	}
}

func (api *API) UserSearch(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := req.URL.Query()

	fmt.Println(queryParams)
	name := queryParams.Get("name")
	users := api.users.SearchUsers(name)

	err := json.NewEncoder(w).Encode(users)
	if err != nil {
		http.Error(w, "Could not return users list", http.StatusBadRequest)
		return
	}
}
