FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN apt-get update && \
	apt-get install -y curl && \
	rm -rf /var/lib/apt/lists/* && \
	mvn clean package -DskipTests -Dgcf.skip

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
