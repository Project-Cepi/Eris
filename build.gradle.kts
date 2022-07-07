import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.dokka") version "1.5.31"
    // Kotlinx serialization for any data format
    kotlin("plugin.serialization") version "1.7.10"

    // Apply the application plugin to add support for building a jar
    java

    // Apply maven-publish to publish to github packages
    `maven-publish`
}

repositories {
    // Use mavenCentral
    mavenCentral()

    maven(url = "https://jitpack.io")
    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://repo.velocitypowered.com/snapshots/")
}


dependencies {
    // Align versions of all Kotlin components
    compileOnly(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    compileOnly(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    compileOnly(kotlin("reflect"))

    // Use the JUpiter test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Compile Minestom into project
    compileOnly("com.github.Minestom:Minestom:7867313290")

    // import kotlinx serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    // Javacord
    implementation("org.javacord", "javacord", "3.3.2")

    // implement KStom
    compileOnly("com.github.Project-Cepi:KStom:82f7000079")

    // Add EnergyExtension
    compileOnly("com.github.Project-Cepi:EnergyExtension:d6343c100c")

    // Add LevelExtension
    compileOnly("com.github.Project-Cepi:LevelExtension:cae5707195")

    implementation("net.kyori:adventure-text-minimessage:4.10.1-SNAPSHOT")

    // Jansi
    compileOnly("org.jline:jline-terminal-jansi:3.21.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("eris")
        mergeServiceFiles()

    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}