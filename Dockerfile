FROM openjdk:17.0.2-oraclelinux8

RUN mkdir -p /app
WORKDIR /app

COPY build/libs/strava-*-SNAPSHOT.jar ./strava.jar


CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "strava.jar"]

EXPOSE 8080