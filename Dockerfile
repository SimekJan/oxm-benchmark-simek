FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/oxm-benchmark-simek-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-cp", "/app/app.jar"]