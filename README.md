### Strava Token Generator

[![Java CI with Gradle](https://github.com/james-millner/kotlin-strava-app/actions/workflows/gradle.yml/badge.svg)](https://github.com/james-millner/kotlin-strava-app/actions/workflows/gradle.yml)

What started as a simple Kotlin Spring Boot app designed as a bit of a playground for the Strava API, has become a small system to generate Strava API tokens.

The app is very much a work in progress, and is being developed in my spare time.

This application current provides you with: 

* The mechanism to get a Strava API token for further interaction with the API.
  * Documentation incoming

### Requirements

* Docker
* \>= JDK 8 
  * JDK 19 Recommended

### Build app & Docker Image

1. `./gradlew clean build jibDockerBuild` 
2. Validate docker image has been created:

````
docker images | grep strava
jmillnerdev/strava-token-generator                   latest              e6b002a72934        About a minute ago   297MB
````

### Docker Hub
Alternatively the docker image is published to the docker hub. Available here: https://hub.docker.com/r/jmillnerdev/strava-app

### Run Instructions

#### Prerequisites 

The strava app has some defined configuration that needs to be injected before running. These values need to be provided as environment variables at runtime.

````
strava:
  clientId: ${STRAVA_CLIENT_ID}
  clientSecret: ${STRAVA_CLIENT_SECRET}
````
