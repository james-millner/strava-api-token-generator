### Kotlin Strava Spring Boot Application
A simple Kotlin Spring Boot app designed as abit of a playground for the Strava API.

The app has a variety of API endpoints available to use. To find out more about these, boot up the app and head to:

`http://localhost:8080/swagger-ui.html`

The app is very much a work in progress and its only purpose is to be a side project to play with Kotlin.

### Build app & Docker Image

1. `mvn clean package jib:dockerBuild` | `make` - This defaults to the make build step in the provided makefile.
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
  url: ${STRAVA_BASEURL}
  accessToken: ${STRAVA_ACCESS_TOKEN}
  clientId: ${STRAVA_CLIENT_ID}
  clientSecret: ${STRAVA_CLIENT_SECRET}
  OAuthUrl: ${STRAVA_OAUTH_URL}
````

| Environment Variable         | Value                                          | 
| -----------------------------|:----------------------------------------------:| 
| STRAVA_BASEURL               | https://www.strava.com/oauth/mobile/authorize  |
| STRAVA_OAUTH_URL             | https://www.strava.com/oauth/token             |

#### docker-compose

1. `STRAVA_BASEURL=X STRAVA_ACCESS_TOKEN=J... docker-compose up`

#### Kubernetes
This step currently requires Kompose to be installed. This can be found at https://kompose.io

1. `STRAVA_BASEURL=X STRAVA_ACCESS_TOKEN=J... kompose up`
