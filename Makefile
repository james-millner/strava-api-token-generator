PACKAGE  = strava

TAG := $$(git log -1 --pretty=%h)
IMG := jmillnerdev/strava-app:${TAG}

default: build

build: clean package build-docker-image

build-and-push: build push

clean:
	mvn clean

package:
	mvn package

build-docker-image:
	TAGS=${TAG} mvn jib:dockerBuild

push:
	docker push ${IMG}

build-prometheus:
	cd prometheus
	docker build -t jmillnerdev/prometheus-spring .
	docker push jmillnerdev/prometheus-spring

#TAGS=${TAG} STRAVA_ACCESS_TOKEN=foobar STRAVA_CLIENT_ID=1234 STRAVA_CLIENT_SECRET=foobar docker-compose up
docker-run:
	TAGS=${TAG} STRAVA_ACCESS_TOKEN=146ed1057b45523c22cbbd3a2e439881459b7e90;STRAVA_CLIENT_ID=34228 STRAVA_CLIENT_SECRET=0ca959e47010679c188c4c3b3d3554c6ed109ac0 STRAVA_BASEURL=https://www.strava.com/oauth/mobile/authorize STRAVA_OAUTH_URL=https://www.strava.com/oauth/token docker-compose up

docker-clean:
	docker kill $(docker ps -q)
