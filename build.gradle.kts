plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

group = "com.chrystowicz.bowling"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}

kover {
    reports {
        verify {
            rule {
                minBound(100)
            }
        }
    }
}
