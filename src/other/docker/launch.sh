#!/bin/bash

rm -rf backend/jar/*

cd ../..

./gradlew clean bootJar > /dev/null

SOURCE_DIR="./"

TARGET_DIR="./other/docker/backend/jar"

mkdir -p "$TARGET_DIR"

SPECIFIC_DIRS=(
    "./AuthService"
    "./GameService"
    "./Gateway"
)

for DIR in "${SPECIFIC_DIRS[@]}"; do

    LIB_DIR="$DIR/build/libs"

    find "$LIB_DIR" -type f -name "*.jar" -exec cp {} "$TARGET_DIR" \;

done

cd ./other/docker

docker-compose down

docker-compose up --build -d
docker image prune -f
