plugins {
    kotlin("multiplatform") version "1.9.0"
    alias(libs.plugins.graphqlServerPlugin)
    alias(libs.plugins.ktorPlugin)
    alias(libs.plugins.apollographqlPlugin)
    application
}

group = "me.stefanofuri"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val jvmMain by getting {
            dependencies {
                // Ktor
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.websockets)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.serialization.jackson)

                // GraphQL Server
                implementation(libs.graphql.kotlin.ktor.server)
                implementation(libs.graphql.kotlin.server)

                // Logger
                implementation(libs.logback)

                // Apollo GraphQL
                implementation(libs.apollo.runtime)
                implementation(libs.okhttp.interceptor)
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(libs.letsplot.js)
                implementation(libs.kotlin.coroutines.core)
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("me.stefanofuri.application.server.GraphQLServerApplicationKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}