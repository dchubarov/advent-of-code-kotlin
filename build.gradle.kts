plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

tasks {
    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "8.4"
    }
}
