plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jetbrains.dokka") version "1.4.0-rc"

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
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Use the JUpiter test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

    // Compile Minestom into project
    implementation("com.github.Minestom:Minestom:b84bcde84d")

    // Use the Netty library
    implementation("io.netty:netty-transport-native-epoll:4.1.52.Final")

    // KHTTP
    implementation("khttp:khttp:1.0.0")

    // Implement Klaxon
    implementation("com.beust:klaxon:5.0.1")

    // Javacord
    implementation("org.javacord", "javacord", "3.1.1")
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
tasks.jar {
    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.RSA", "META-INF/*.SF","META-INF/*.DSA")
    }
}