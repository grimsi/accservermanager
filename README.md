# AC Competizione Server Manager - Backend

## Introduction
This is the backend component of my WIP AC Competizione Server Manager.
For now the server manager will just consist of a backend which provides a simple RESTful API.
You can take a look at the API spec by reading the "api-spec.yaml" or copying the content of this file into the [Swagger.io Editor](http://editor.swagger.io/) to view a graphical representation.

## Goal of this project
This project aims to provide a robust and reliable management interface to manage a great number of gameserver instances for AC Competizione.
The primary goal of this project is **not** to compete with existing server managers such as [gotzls accservermanager](https://github.com/gotzl/accservermanager) but rather target more professional users. For this reason there will be **no** UI in the beginning, but it should be very easy for interested developers to implement their own UI based on my API specification.
The main goal for me is to learn how to work with a lot of different cutting edge technologies, so don't expect too much from my source code since most of the stuff I will work with is pretty new to me.

## Technology Stack
The backend is built using Java Spring Boot, Spring Metrics, Spring Security and Spring Data.
MongoDB is used to persist data.
The instances are hosted in dedicated docker containers which are managed with the [Spotify Docker Client for Java](https://github.com/spotify/docker-client).
The password is encrypted using BCrypt, so dont worry about hackers being able to read if from the database (although it has to be stored in plain text in the 'application.properties' file until there is an onboarding process for new users).
The API is secured with stateless JSON Web Tokens which are signed by befault by a random UUID (although this can be overridden by the user, which I do not recommend if its not really necessary).
All API input is validated and the API wont leak any stack traces but instead returns (hopefully) useful information about the error including the correct HTTP Status.

## Current functionality
The core functionality is done. The API is secured via JWT and needs user and password to login, except for "/info", ":9554/prometheus" and "/login" which can be used without authentication.
You can create, udpate, read and delete events and instances, you can link instances to events and you can start, stop, pause and resume instances. Each instance will be deployed in its own docker container with the necessary ports mapped to the ports on your host. Just make sure your firewall does not block traffic on the ports you defined. 

## Next steps
Next I will need to create a docker container for the backend itself including the database.
Last but not least I want to integrate monitoring of the backend and the server instances. This can be very easy or very hard depending on the official documentation of Kunos. The metrics will be exposed via the prometheus endpoint (which is already working on ":9554/prometheus" but its just exposing generic information about the java runtime of the backend).
When all this is finally done and working I will maybe implement my own frontend and my server manager as a single ready-to-use container on Docker Hub, which will make it very easy for interested users to install the application.

## How can I help?
If you want you can write your own frontend. Just send me a short message or open an issue and I will gladly try to help you :)
If you see a bug and want to fix it yourself, go on. Open a pull request and I will merge it :)
If you just want to give suggestions, open an issue and I will try to help you.

## License
This source code will be licensed under the ["Dont Be a Dick" Public License](https://dbad-license.org/).
