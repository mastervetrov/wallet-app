FROM gradle:8.14.3-jdk17 AS builder

WORKDIR /app

COPY .. .

RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar wallet.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "wallet.jar"]