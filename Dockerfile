FROM openjdk:17.0.2-oraclelinux8

RUN mkdir -p /app
WORKDIR /app

COPY build/libs/strava-*-SNAPSHOT.jar ./strava.jar
COPY start-up.sh .

ENTRYPOINT ["java -jar $JAVA_ARGS \"-Dspring.profiles.active=docker\" strava.jar"]

EXPOSE 8080