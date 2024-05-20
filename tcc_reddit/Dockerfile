FROM maven:3.8.4 as build

WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine

COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
