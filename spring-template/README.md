# Overview
This template provides a very basic Spring Boot application. This is intended to provide a bare minimum set of files that is executable, and can be compiled into a functional docker image.

# Running with docker-compose
If no changes have been made, can bring up the service using:

docker-compose up -d

If changes have been made, make sure we rebuild the image.

docker-compose up -d --build


To stop the service:

docker-compose down
