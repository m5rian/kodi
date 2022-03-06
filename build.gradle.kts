plugins {
    kotlin("jvm") version "1.6.10"
}

group = "net.pringles.kodi"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.0")
    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("io.ktor:ktor-client-json:1.6.7")
    implementation("io.ktor:ktor-client-websockets:1.6.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("ch.qos.logback:logback-classic:1.2.10")
}
