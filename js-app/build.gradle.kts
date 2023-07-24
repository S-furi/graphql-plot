@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatformPlugin)
    alias(libs.plugins.apollographqlPlugin)
}

repositories {
    mavenCentral()
}


kotlin {
    sourceSets {
        js(IR) {
            binaries.executable()
            browser {
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.apollo.runtime)
                implementation(libs.logback)
                implementation(libs.letsplot.js)
                implementation(libs.kotlin.coroutines.core)
            }

        }
    }
}

apollo {
    service("service") {
        packageName.set("jsMain.client")
        srcDir("src/jsMain/resources/graphql")
    }
}