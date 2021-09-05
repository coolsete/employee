FROM openjdk:8-jdk-alpine

RUN mkdir /app

COPY ./build/libs/framey-*-SNAPSHOT.jar /app/framey.jar
EXPOSE 8088
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar","/app/framey.jar"]
