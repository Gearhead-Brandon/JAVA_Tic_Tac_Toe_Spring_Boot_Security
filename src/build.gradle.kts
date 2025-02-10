import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("java")
    id("jacoco")
}

tasks.bootJar {
    enabled = false
}

var assertjVersion = "3.27.2"
var junitJupiterVersion = "5.10.0"
var lombokVersion = "1.18.36"
var mapstructVersion = "1.6.3"

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
    }

    java{
        sourceCompatibility = JavaVersion.VERSION_23
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(23))
        }
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }

//    developmentOnly {
//        extendsFrom(configurations.implementation.get())
//    }
    }

    ext {
        set("springCloudNetflixEurekaServerVersion", "2024.0.0")
    }

//
//    if (project.name == "Gateway") {
//        dependencies {
//            implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.2.0") {
//                exclude(group = ("org.springframework.boot"), module = "spring-boot-starter-web")
//            }
//        }
//    }

    dependencies {
        "implementation"("org.springframework.boot:spring-boot-starter")
        "implementation"("org.springframework.boot:spring-boot-starter-web")
//        "implementation"("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        "implementation"("org.springframework.cloud:spring-cloud-starter-zookeeper-discovery")

        "implementation"("org.springframework.boot:spring-boot-starter-validation")
//        "implementation"("org.springframework.boot:spring-boot-starter-security")

        "compileOnly"("org.mapstruct:mapstruct:$mapstructVersion")
        "compileOnly"("org.projectlombok:lombok:$lombokVersion")

        "annotationProcessor"("org.projectlombok:lombok:$lombokVersion")
        "annotationProcessor"("org.mapstruct:mapstruct-processor:$mapstructVersion")

        "testImplementation"(platform("org.junit:junit-bom:$junitJupiterVersion"))
        "testImplementation"("org.junit.jupiter:junit-jupiter")
        "testImplementation"("org.assertj:assertj-core:$assertjVersion")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom(
                "org.springframework.cloud:spring-cloud-dependencies:${property("springCloudNetflixEurekaServerVersion")}"
            )
        }
    }
}

version = "1.0.0"

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
    }

    finalizedBy(tasks.jacocoTestReport)
}