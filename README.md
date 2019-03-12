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
The backend will make heavy use of the Java Spring framework, including Spring Security, Spring Data and Spring Metrics.
For persistence I want to use MongoDB since it probably is the most flexible NoSQL database and has great support for a variety of use cases.
I don't plan on implementing a user management but if there is a need for it I would strongly suggest using strong password hashing algorithms like BCrypt. Password security is no joke and sadly I have seen it done wrong even by big companies, so I want to do it right.
The backend will be deployed in a Docker container, not because it is needed but because I want to learn how to work with Docker and how to manage your own images. Also this makes deployment easier in a production environment.
The gameservers will also be deployed in sibling containers, so each gameserver instanceDto will have its own docker container. This makes them manageable and will probably improve security if done correctly.
If I decide to provide a frontend myself it will most certainly be a Angular based web frontend since this also is widely adopted and I already know my way with Angular. But a frontend is not in the scope of the first versions apart from the automatically generated Swagger UI which can be used although it's meant for testing the API.

## How can I help?
In the beginning you sadly can't really help me because I need to define the API first. But once that's done there is nobody stopping you from developing your custom UI which works with my backend. The current version of the API spec will always be publicy available. But remember, since we haven't hit version 1.0.0 *yet* [the API is still not stable and can always change, breaking current implementations](https://semver.org/#spec-item-4).

## License
This source code will be licensed under the ["Dont Be a Dick" Public License](https://dbad-license.org/).
