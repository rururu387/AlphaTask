plugins {
    id("java")
}

group = "org.test"
version = "1.0-SNAPSHOT"

val springBootVersion by extra { "2.7.0" }
val feignVersion by extra { "11.8" }
val junitVersion by extra { "5.8.1" }
val lombokVersion by extra { "1.18.24" }
val gsonVersion by extra { "2.9.0" }

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // https://mvnrepository.com/artifact/io.github.openfeign/feign-jackson
    implementation("io.github.openfeign:feign-jackson:$feignVersion")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}