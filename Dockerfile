FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY . .
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]