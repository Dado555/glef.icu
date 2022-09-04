package api

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"net/http"
)

func (api *API) SearchTags(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	params := mux.Vars(request)
	name, _ := params["name"]
	tags := api.tags.SearchTags(name)

	err := json.NewEncoder(w).Encode(tags)
	if err != nil {
		http.Error(w, "Could not return tags list", http.StatusBadRequest)
		return
	}
}
