# syntax=docker/dockerfile:1
FROM maven:3-openjdk-11 AS build
RUN mkdir /usr/parent
# the docker build command needs to run on the parent folder with -f app/Dockerfile
COPY . /usr/parent
WORKDIR /usr/parent
RUN mvn clean package -DskipTests

# https://hub.docker.com/r/adoptopenjdk/openjdk11/tags?page=1&ordering=last_updated&name=alpine
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
#FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app
COPY --from=build /usr/parent/app/target/app-0.1.jar /app
WORKDIR /app
CMD java -jar app-0.1.jar