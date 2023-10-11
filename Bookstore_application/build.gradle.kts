
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val slf4j_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val hikaricp_version: String by project


plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    application
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-locations:$ktor_version")
    implementation("org.jetbrains.exposed","exposed-core","0.31.1")
    implementation("org.jetbrains.exposed","exposed-jdbc","0.31.1")
    implementation ("org.jetbrains.exposed","exposed-dao","0.31.1")
    implementation("org.postgresql","postgresql","42.2.18")
    implementation("com.zaxxer","HikariCP","4.0.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.konform:konform:0.4.0")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
