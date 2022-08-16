package downloadPlay

import (
	"fmt"
	"github.com/artdarek/go-unzip"
	"github.com/cavaliergopher/grab/v3"
	"log"
	"os"
	"path/filepath"
)

func getSubtitleFilePath(folderName string) string {
	var files []string
	var subtitlePath = ""

	root := "/tmp/" + folderName
	err := filepath.Walk(root, func(path string, info os.FileInfo, err error) error {
		files = append(files, path)
		return nil
	})
	if err != nil {
		panic(err)
	}
	for _, file := range files {
		if filepath.Ext(file) == ".srt" {
			subtitlePath = file
		}
		fmt.Println(file)
	}

	return subtitlePath
}

func DownloadSubtitle(subtitleWebPath string) string {
	resp, err := grab.Get("/tmp", subtitleWebPath)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("Download saved to", resp.Filename)
	fmt.Println("Response: ", resp)

	var folderName = resp.Filename[5 : len(resp.Filename)-4]
	fmt.Println(folderName)
	UnzipFile(resp.Filename, "/tmp/"+folderName)

	return folderName
}

func UnzipFile(src string, dest string) {
	uz := unzip.New(src, dest)

	err := uz.Extract()
	if err != nil {
		fmt.Println(err)
	}
}
