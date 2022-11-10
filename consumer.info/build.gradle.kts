import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream
import kotlin.collections.listOf

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("au.com.dius.pact") version "4.3.16"
}

group = "swe.dependencies.consumer"
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
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("au.com.dius.pact.consumer:junit5:4.3.16")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootRun {
    jvmArgs = listOf("-Dspring.output.ansi.enabled=ALWAYS")
}

tasks.register<Copy>("copyPacts") {
    description = "Copies the generated Pact json to the provider resources directory"
    from("build/pacts/")
    into("../provider.customer/src/test/resources/pacts")
}

pact {
    publish {
        pactDirectory = "consumer.info/build/pacts"
        pactBrokerUrl = "http://localhost:8000"
        pactBrokerUsername = "pact_customer"
        pactBrokerPassword = "pact_customer"
        tags = listOf(gitBranch(), "test", "prod")
        consumerVersion = gitHash()
    }
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