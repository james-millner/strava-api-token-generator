PACKAGE  = strava

TAG := $$(git log -1 --pretty=%h)
IMG := jmillnerdev/strava-app:${TAG}

clean-package:
	TAGS=${TAG} mvn clean install package jib:dockerBuild

docker-push:
	docker push jmillnerdev/strava-app:${TAG}

default: clean-package
