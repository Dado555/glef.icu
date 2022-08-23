#!/bin/bash

while getopts s: flag
do
    case "${flag}" in
        s) start=${OPTARG};;
    esac
done

if [[ "$start" == "local" ]];then 
    echo "Starting system localy..."

    sudo service nginx start

    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/gleficu-frontend -- npm run serve
    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/user-service -- go run server.go
    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/user-movies-service -- go run server.go
    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/scrape-movie-service -- go run server.go
    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/grading-service -- cargo run --color=always --package grading-service --bin grading-service
    gnome-terminal --tab --working-directory=/home/dado555/Desktop/gleficu/glef.icu/python-merge -- /home/dado555/Desktop/gleficu/glef.icu/python-merge/venv/bin/python -m flask run

fi