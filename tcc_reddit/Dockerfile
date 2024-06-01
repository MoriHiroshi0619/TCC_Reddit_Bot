FROM maven:3.8.4 as build

#criando e mudando para o DIR app
WORKDIR /app

COPY pom.xml /app
COPY src /app/src

#gerando o .jar da minha aplcação
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine

#descomapctando o .jar da minha aplicação para o meu container
COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
