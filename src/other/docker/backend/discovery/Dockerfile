FROM openjdk:23

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY docker/backend/jar/Discovery.jar /app

# Запускаем JAR-файл
ENTRYPOINT ["java","-jar", "/app/Discovery.jar"]