FROM docker.io/maven:3.9.0 as build
WORKDIR /home/usr/app
COPY pom.xml .
COPY src ./src
RUN mvn verify --fail-never
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/usr/app/target/security-0.0.1-SNAPSHOT.jar /app/security.jar
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "security.jar"]

