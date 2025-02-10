# FROM openjdk:23-alpine
FROM openjdk:23

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY docker/backend/jar/GameService.jar /app

# Указываем порт, на котором будет работать приложение
EXPOSE 8081

# Устанавливаем переменные окружения (по желанию)
# ENV JAVA_OPTS="-Xmx256m -Xms256m"

# Запускаем JAR-файл
ENTRYPOINT ["java", "-jar","/app/GameService.jar"]