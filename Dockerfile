FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests -B


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S anchor \
    && adduser -S anchor -G anchor

COPY --from=build \
    /app/target/anchor-backend-0.0.1-SNAPSHOT.jar \
    app.jar

USER anchor

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]