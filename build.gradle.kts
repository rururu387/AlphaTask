import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    application
    java
}

group = "org.test"
version = "1.0-SNAPSHOT"

val springBootVersion by extra { "2.7.0" }
val lombokVersion by extra { "1.18.24" }
val springCloudOpenFeignVersion by extra { "3.1.3" }

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-contract-wiremock
    implementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.3")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$springCloudOpenFeignVersion")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
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
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}