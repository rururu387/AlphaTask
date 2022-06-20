FROM gradle:7.4.2 AS GRADLE_BUILD
WORKDIR /jar
COPY . .
RUN ./gradlew clean bootJar
FROM openjdk:17
COPY --from=GRADLE_BUILD /jar/build/libs/AlphaTask-1.0-SNAPSHOT.jar /AlphaTaskFatServer.jar
CMD ["java", "-jar", "./AlphaTaskFatServer.jar"]
