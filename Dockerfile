FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
# Skip tests during build
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8050

COPY --from=build /target/ServicesTiers-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
