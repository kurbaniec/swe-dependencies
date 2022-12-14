import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "swe.dependencies.provider"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("au.com.dius.pact.provider:junit5:4.3.16")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    if (System.getProperty("pactPublishResults") == "true") {
        systemProperty("pact.provider.version", gitHash())
        systemProperty("pact.provider.tag", gitBranch())
        systemProperty("pact.verifier.publishResults", "true")
    }
}

tasks.bootRun {
    jvmArgs = listOf("-Dspring.output.ansi.enabled=ALWAYS")
}

fun gitHash(): String {
    val stdOut = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdOut
    }
    return stdOut.toString().trim()
}

fun gitBranch(): String {
    val stdOut = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
        standardOutput = stdOut
    }
    return stdOut.toString().trim()
}