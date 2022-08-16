package main

import (
	"fmt"
	"github.com/Dado555/glef.icu/scrape-movie-service/utils"
	"os/exec"
)

func HandleWatchTorrentMagnet(magnet string) {
	path, _ := utils.IsInstalledCMD("peerflix")

	cmd := exec.Command("peerflix", "--vlc", magnet)
	cmd.Dir = path

	if err := cmd.Run(); err != nil {
		fmt.Printf("\n%s: %v\n", "Failed to run Peerflix", err)
	}

	if err := cmd.Wait(); err != nil {
		fmt.Printf("\n%s: %v\n", "Peerflix returned an error", err)
	}
}

func main() {
	//connStr := "host=localhost port=5432 user=postgres dbname=movieService password=root"
	//
	//corsHandler := cors.New(cors.Options{
	//	AllowedOrigins: []string{"*"},
	//	AllowedMethods: []string{http.MethodPost, http.MethodConnect, http.MethodGet, http.MethodDelete,
	//		http.MethodHead, http.MethodOptions, http.MethodPut, http.MethodTrace},
	//	AllowedHeaders:   []string{"*"},
	//	AllowCredentials: false,
	//})
	//
	//db := models.NewPostgresDatabase(connStr)
	//api := api2.CreateAPI(db)
	//routes := routes2.CreateRoutes(api)
	//
	//handler := corsHandler.Handler(routes)
	//err := http.ListenAndServe(":3001", handler)
	//if err != nil {
	//	panic(err)
	//	return
	//}
	HandleWatchTorrentMagnet("magnet:?xt=urn:btih:F08FFD1B898307A70600F5BCBCC95839804025D7&dn=Harry+Potter+and+the+Deathly+Hallows%3A+Part+2&trudp://open.demonii.com:1337/announce&trudp://tracker.openbittorrent.com:80&trudp://tracker.coppersurfer.tk:6969&trudp://glotorrents.pw:6969/announce&trudp://tracker.opentrackr.org:1337/announce&trudp://torrent.gresille.org:80/announce&trudp://p4p.arenabg.com:1337&trudp://tracker.leechers-paradise.org:6969")
}
