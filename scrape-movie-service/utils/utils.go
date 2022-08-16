package utils

import "os"

func IsInstalledCMD(cmd string) (string, bool) {
	paths := []string{"/usr/bin/", "/usr/local/bin/"}
	for i := 0; i < len(paths); i++ {
		if IsFileExists(paths[i] + cmd) {
			return paths[i], true
		}
	}
	return "", false
}

func IsFileExists(filename string) bool {
	info, err := os.Stat(filename)
	if os.IsNotExist(err) {
		return false
	}
	return !info.IsDir()
}
