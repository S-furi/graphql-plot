@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatformPlugin)
    alias(libs.plugins.graphqlServerPlugin)
    alias(libs.plugins.ktorPlugin)
    application
}

application {
    mainClass.set("gql.plt.server.GraphQLServerApplicationKt")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
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
            }
        }
    }
}