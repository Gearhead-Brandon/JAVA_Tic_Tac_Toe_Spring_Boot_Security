import org.gradle.api.tasks.testing.logging.TestExceptionFormat

//plugins {
//    id("org.springframework.boot") version "3.4.0"
//    id("io.spring.dependency-management") version "1.1.6"
//    id("java")
////    id("jacoco")
//}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "java")
apply(plugin = "jacoco")

group = "game.security.tictactoe"
version = "1.0-SNAPSHOT"

var mainClassName = "game.security.tictactoe.application.TicTacToeApplication"
//var lombokVersion = "1.18.36"
//var mapstructVersion = "1.6.3"
//var junitJupiterVersion = "5.10.0"
var openapiVersion = "2.7.0"
//var assertjVersion = "3.27.2"
var liquibaseVersion = "4.30.0"
var postgresqlVersion = "42.7.4"
var hibernateValidatorVersion = "9.0.0.CR1"

var jacocoExclude = listOf(
    "game/security/tictactoe/application/TicTacToeApplication.class",
    "game/security/tictactoe/configuration/CorsConfig.class",
    "game/security/tictactoe/configuration/SwaggerConfig.class"
)

//java{
//    sourceCompatibility = JavaVersion.VERSION_23
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(23))
//    }
//}

//configurations {
//    compileOnly {
//        extendsFrom(configurations.annotationProcessor.get())
//    }
//
////    developmentOnly {
////        extendsFrom(configurations.implementation.get())
////    }
//}

//repositories {
//    mavenCentral()
//}

//ext {
//    set("springCloudNetflixEurekaServerVersion", "2024.0.0")
//}

tasks.bootJar {
    archiveBaseName.set("GameService") // Устанавливаем новое имя без версии
    archiveVersion.set("")        // Устанавливаем версию
    archiveClassifier.set("")          // Убираем дополнительный классификатор, если не нужен
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.hibernate.validator:hibernate-validator:$hibernateValidatorVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openapiVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
}

//tasks.compileJava.configure {
//    options.compilerArgs.add("-parameters")
//}

//dependencyManagement {
//    imports {
//        mavenBom(
//            "org.springframework.cloud:spring-cloud-dependencies:${property("springCloudNetflixEurekaServerVersion")}"
//        )
//    }
//}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    classDirectories.setFrom(classDirectories.files.map {
        fileTree(it).matching {
            exclude(jacocoExclude)
        }
    })
}
