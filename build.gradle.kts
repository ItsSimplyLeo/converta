import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
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
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")  // Environment variable loader
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("dev.converta.bot.MainKt")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}