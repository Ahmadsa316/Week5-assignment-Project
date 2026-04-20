FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y \
    openjfx \
    libgtk-3-0 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/week4-assignment-1.0-SNAPSHOT.jar app.jar

CMD ["java", "--module-path", "/usr/share/openjfx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]