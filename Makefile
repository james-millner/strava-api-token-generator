PACKAGE  = strava

TAG := $$(git log -1 --pretty=%h)
IMG := jmillnerdev/strava-app:${TAG}

clean-package:
	TAGS=${TAG} mvn clean install package jib:dockerBuild

docker-push:
	docker push jmillnerdev/strava-app:${TAG}

build-prometheus:
	cd prometheus && \
	docker build -t jmillnerdev/prometheus-spring . && \
	docker push jmillnerdev/prometheus-spring

docker-run:
	TAGS=${TAG} docker-compose up


default: clean-package
