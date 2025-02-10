//plugins {
//    id("org.springframework.boot") version "3.4.0"
//    id("io.spring.dependency-management") version "1.1.6"
//    id("java")
//}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "java")

group = "game.gateway.tictactoe"
version = "1.0-SNAPSHOT"

var mainClassName = "game.gateway.tictactoe.application.GatewayApplication"
var springCloudVersion = "4.2.0"

//java{
//    sourceCompatibility = JavaVersion.VERSION_23
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(23))
//    }
//}

//repositories {
//    mavenCentral()
//}

//ext {
//    set("springCloudNetflixEurekaServerVersion", "2024.0.0")
//}

tasks.bootJar {
    archiveBaseName.set("Gateway") // Устанавливаем новое имя без версии
    archiveVersion.set("")        // Устанавливаем версию
    archiveClassifier.set("")          // Убираем дополнительный классификатор, если не нужен
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$springCloudVersion")
//    implementation("org.springframework.cloud:spring-cloud-starter-gateway:$springCloudVersion")
}

//dependencyManagement {
//    imports {
//        mavenBom(
//            "org.springframework.cloud:spring-cloud-dependencies:${property("springCloudNetflixEurekaServerVersion")}"
//        )
//    }
//}

//tasks.compileJava.configure {
//    options.compilerArgs.add("-parameters")
//}

//tasks.jar {
//    manifest {
//        attributes["Main-Class"] = mainClassName
//        attributes["Class-Path"] = configurations.compileClasspath.get().joinToString(" ")
//    }
//}