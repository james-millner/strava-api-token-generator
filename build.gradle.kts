/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://jcenter.bintray.com/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.5")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.5")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.json:json:20220320")
    implementation("io.micrometer:micrometer-registry-prometheus:1.8.4")
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.6.5")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("com.google.maps:google-maps-services:2.0.0")


    implementation("khttp:khttp:1.0.0")


    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.5")
    testImplementation("io.mockk:mockk:1.12.3")
}

group = "com.jm"
version = "0.0.1-SNAPSHOT"
description = "strava"

tasks.withType<Test> {
    useJUnitPlatform()
}
