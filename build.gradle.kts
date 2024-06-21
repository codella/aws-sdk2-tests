plugins {
    id("java")
}

group = "dk.codella"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.19.8"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))

    testImplementation("org.testcontainers:localstack")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("software.amazon.awssdk:s3:2.26.5")

    testImplementation("ch.qos.logback:logback-core:1.5.6")
    testImplementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.test {
    useJUnitPlatform()
}