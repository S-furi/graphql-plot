plugins {
    `kotlin-dsl`
    alias(libs.plugins.ktorPlugin)
    // alias(libs.plugins.kotlinSerializationPlugin)
    alias(libs.plugins.graphqlServerPlugin)
    application
}

application {
    mainClass.set("plotting.server.GraphQLServerApplicationKt")
}

repositories { mavenCentral() }

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.serialization.jackson)

    implementation(libs.logback)
    implementation(libs.junit)
    implementation(libs.graphql.kotlin.server)
    implementation(libs.graphql.kotlin.ktor.server)
}

tasks.shadowJar {
    setProperty("zip64", true)
}
