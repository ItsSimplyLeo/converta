import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.gradleup.shadow") version "9.0.0-rc1"
    kotlin("jvm") version "2.2.0"
    application
}

group = "dev.converta.bot"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.6.1")   // Discord API library
    implementation("ch.qos.logback:logback-classic:1.5.18") // Logging framework
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")  // Environment variable loader
    implementation(kotlin("stdlib"))
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

application {
    mainClass.set("dev.converta.bot.MainKt")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("converta.jar")
        manifest {
            attributes(mapOf("Main-Class" to "dev.converta.bot.MainKt"))
        }
    }
}