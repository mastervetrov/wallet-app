FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

ENV JAVA_TOOL_OPTIONS="-Xmx3g -Xms3g"

WORKDIR /app

COPY --from=builder /app/build/libs/wallet-1.0.0.jar wallet.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_TOOL_OPTIONS -jar /app/wallet.jar"]