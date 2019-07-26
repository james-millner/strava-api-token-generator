PACKAGE  = strava

TAG := $$(git log -1 --pretty=%h)
IMG := jmillnerdev/strava-app:${TAG}

default: build

build: clean package build-docker-image

build-and-push: build push

clean:
	./mvnw clean

package:
	./mvnw package

build-docker-image:
	TAGS=${TAG} ./mvnw jib:dockerBuild

push:
	docker push ${IMG}

#TAGS=${TAG} STRAVA_ACCESS_TOKEN=foobar STRAVA_CLIENT_ID=1234 STRAVA_CLIENT_SECRET=foobar docker-compose up
docker-run:
	TAGS=${TAG} STRAVA_ACCESS_TOKEN=test STRAVA_CLIENT_ID=1234 STRAVA_CLIENT_SECRET=test STRAVA_BASEURL=https://www.strava.com/oauth/mobile/authorize STRAVA_OAUTH_URL=https://www.strava.com/oauth/token docker-compose up

create-k8s-manifests:
	 kompose convert -f docker-compose.yaml -o ./kubernetes/
