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

#### Pull from Docker Hub

1. `docker pull jmillnerdev/strava-app`
2. `docker run jmillnerdev/strava-app -p 8080:8080`


