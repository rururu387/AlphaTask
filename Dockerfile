FROM gradle:7.4.2 AS GRADLE_BUILD
COPY . .
RUN ./gradlew build
FROM openjdk:17
WORKDIR /app

RUN ./gradlew build
