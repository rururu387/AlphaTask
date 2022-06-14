plugins {
    id("java")
}

group = "org.test"
version = "1.0-SNAPSHOT"

val springBootVersion by extra { "2.7.0" }
val feignVersion by extra { "11.8" }
val junitVersion by extra { "5.8.1" }

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // https://mvnrepository.com/artifact/io.github.openfeign/feign-core
    implementation("io.github.openfeign:feign-core:$feignVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}