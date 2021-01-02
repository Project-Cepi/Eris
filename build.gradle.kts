import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jetbrains.dokka") version "1.4.20"

    // Apply the application plugin to add support for building a jar
    java

    // Apply maven-publish to publish to github packages
    `maven-publish`
}

repositories {
    // Use jcenter for resolving dependencies.
    jcenter()
    mavenCentral()
    // Use mavenCentral
    maven(url = "https://repo1.maven.org/maven2/")
    maven(url = "http://repo.spongepowered.org/maven")
    maven(url = "https://libraries.minecraft.net")
    maven(url = "https://jitpack.io")
    maven(url = "https://jcenter.bintray.com/")
}

dependencies {
    // Align versions of all Kotlin components
    compileOnly(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    compileOnly(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    compileOnly(kotlin("reflect"))

    // Use the JUpiter test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

    // Compile Minestom into project
    compileOnly("com.github.Minestom:Minestom:4d2dd7d256")

    // Javacord
    implementation("org.javacord", "javacord", "3.1.1")

    // implement KStom
    compileOnly("com.github.Project-Cepi:KStom:main-SNAPSHOT")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            // Include any other artifacts here, like javadocs
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Project-Cepi/ChatExtension")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
	}
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("eris")
        mergeServiceFiles()

    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}