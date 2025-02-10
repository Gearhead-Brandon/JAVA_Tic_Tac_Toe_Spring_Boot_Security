FROM openjdk:23

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY docker/backend/jar/Gateway.jar /app

# Запускаем JAR-файл
ENTRYPOINT ["java","-jar", "/app/Gateway.jar"]