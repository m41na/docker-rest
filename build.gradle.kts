import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.10"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
}

group = "works.hop"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.5")

    implementation("io.jsonwebtoken:jjwt-api:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.0")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:6.2.0.Final")

    runtimeOnly("org.postgresql:postgresql")
    implementation("com.googlecode.jmapper-framework:jmapper-core:1.6.0.1")

    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.flywaydb:flyway-core:7.15.0")
    testImplementation("io.zonky.test:embedded-postgres:1.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}