
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Proje dosyalarını konteynıra kopyalamdık böylece
COPY target/Quickbook-0.0.1-SNAPSHOT.jar app.jar

# Render’ın container’ı çalıştırması için port açtım
EXPOSE 8080

# Uygulamayı çalıştırmak için lazımmış
ENTRYPOINT ["java", "-jar", "app.jar"]
