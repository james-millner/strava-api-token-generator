### Kotlin Strava Spring Boot Application
A simple Kotlin Spring Boot app designed for handling GPX files downloaded from Strava.

The app currently provides a single endpoint HTTP endpoint to translate a GPX file into a provided output:
- POST: `/strava/file-upload?outputType=json`

| Params         | Supported Values    | 
| -------------  |:-------------:      | 
| outputType     | json,xml,dataobject | 


#### Example Request
`
curl -F 'file=@<localfile>.gpx' http://localhost:8080/strava/file-upload?outputType=json
`

### Build app & Docker Image

1. `mvn clean package jib:dockerBuild`
2. Validate docker image has been created:

````
docker images | grep strava
jmillnerdev/strava-app                     latest              e6b002a72934        About a minute ago   148MB
````

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

