PACKAGE  = strava

TAG := $$(git log -1 --pretty=%h)
IMG := jmillnerdev/strava-app:${TAG}

default: build

build: clean package build-docker-image

build-and-push: build docker-push

clean:
	mvn clean

package:
	mvn package

build-docker-image:
	TAGS=${TAG} mvn jib:dockerBuild

docker-push:
	docker push ${IMG}

build-prometheus:
	cd prometheus
	docker build -t jmillnerdev/prometheus-spring .
	docker push jmillnerdev/prometheus-spring

#TAGS=${TAG} STRAVA_ACCESS_TOKEN=foobar STRAVA_CLIENT_ID=1234 STRAVA_CLIENT_SECRET=foobar docker-compose up
docker-run:
	TAGS=${TAG} docker-compose up

docker-clean:
	docker kill $(docker ps -q)


