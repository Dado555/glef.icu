package api

import (
	"encoding/json"
	"fmt"
	"net/http"
)

func (api *API) SearchTags(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryParams := request.URL.Query()

	fmt.Println(queryParams)
	name := queryParams.Get("name")
	tags := api.tags.SearchTags(name)

	err := json.NewEncoder(w).Encode(tags)
	if err != nil {
		http.Error(w, "Could not return tags list", http.StatusBadRequest)
		return
	}
}
