group = "com.alpha"
version = "1.0-SNAPSHOT"

plugins {
    application
    java
    id("org.springframework.boot") version "2.7.0"
}

application {
    mainClass.set("com.alpha.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val springBootVersion by extra { "2.7.0" }
val lombokVersion by extra { "1.18.24" }
val slf4jVersion by extra { "1.7.36" }
val springCloudOpenFeignVersion by extra { "3.1.3" }
val wireMockVersion by extra { "3.1.3" }

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-contract-wiremock
    implementation("org.springframework.cloud:spring-cloud-contract-wiremock:$wireMockVersion")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$springCloudOpenFeignVersion")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

configurations.implementation {
    exclude(group = "ch.qos.logback", module = "logback-classic")
}

sourceSets {
    main {
        java {
            srcDir("/src/main")
            exclude("**/webApp/**")
        }
    }
    test {
        java {
            srcDir("/src/test")
            exclude("**/webApp/**")
        }
    }
}

tasks {
    processResources {
        exclude("**/webApp/**")
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }
}