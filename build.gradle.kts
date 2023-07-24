@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatformPlugin)
    alias(libs.plugins.apollographqlPlugin)
    alias(libs.plugins.ktorPlugin)
    alias(libs.plugins.graphqlServerPlugin)
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

    js(IR) {
        binaries.executable()
        browser {
        }
    }

    sourceSets {
        val jsMain by getting {
            kotlin.srcDir("js-app/src")

            dependencies {
                implementation(libs.apollo.runtime)
                implementation(libs.logback)
                implementation(libs.letsplot.js)
                implementation(libs.kotlin.coroutines.core)
            }
        }

        val jvmMain by getting {
            kotlin.srcDir("server/src")
        }
    }
}
