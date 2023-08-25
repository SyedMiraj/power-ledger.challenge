FROM openjdk:8-alpine

#Shell expands *
ARG JAR_FILE=target/*.jar 
EXPOSE 8080/tcp
ENV JAVA_OPTS=""

WORKDIR /app
COPY ${JAR_FILE} app.jar

ENTRYPOINT exec java -jar $JAVA_OPTS /app/app.jar
