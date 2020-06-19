FROM openjdk:11-jre

LABEL maintainer="benjamin.ihrig@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/bachelorparty-server-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} server.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container","-jar","/server.jar"]
