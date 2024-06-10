FROM docker.io/maven:3.9.0 as build
WORKDIR /home/usr/app
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/security-0.0.1-SNAPSHOT.jar security.jar
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "security.jar"]
