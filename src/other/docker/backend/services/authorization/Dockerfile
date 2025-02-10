# FROM openjdk:23-alpine

# jre 23
FROM openjdk:23-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY docker/backend/jar/AuthService.jar /app

# Указываем порт, на котором будет работать приложение
EXPOSE 8082

# Устанавливаем переменные окружения (по желанию)
# ENV JAVA_OPTS="-Xmx256m -Xms256m"

# Запускаем JAR-файл
ENTRYPOINT ["java", "-jar","/app/AuthService.jar"]