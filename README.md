### Kotlin Strava Spring Boot Application

[![Java CI with Gradle](https://github.com/james-millner/kotlin-strava-app/actions/workflows/gradle.yml/badge.svg)](https://github.com/james-millner/kotlin-strava-app/actions/workflows/gradle.yml)

A simple Kotlin Spring Boot app designed as a bit of a playground for the Strava API.

The app is very much a work in progress and its only purpose is to be a side project to play with Kotlin.

This application current provides you with: 

* The mechanism to get a Strava API token for further interaction with the API.
* A GPX API to upload / convert that into JSON format
* A TCX API to upload / convert that into JSON format

### Requirements

* Docker
* \>= JDK 8 
  * JDK 19 Recommended

### Build app & Docker Image

1. `./gradlew clean build jibDockerBuild` 
2. Validate docker image has been created:

````
docker images | grep strava
jmillnerdev/strava-app                     latest              e6b002a72934        About a minute ago   148MB
````

### Docker Hub
Alternatively the docker image is published to the docker hub. Available here: https://hub.docker.com/r/jmillnerdev/strava-app

### Run Instructions

#### Prerequisites 

The strava app has some defined configuration that needs to be injected before running. These values need to be provided as environment variables at runtime.

````
strava:
  accessToken: ${STRAVA_ACCESS_TOKEN}
  clientId: ${STRAVA_CLIENT_ID}
  clientSecret: ${STRAVA_CLIENT_SECRET}
````

#### docker-compose

1. `STRAVA_BASEURL=X STRAVA_ACCESS_TOKEN=J... docker compose up`
