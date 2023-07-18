plugins {
    `kotlin-dsl`
    alias(libs.plugins.apollographqlPlugin)
}

repositories {
    mavenCentral()
}

dependencies { 
    implementation(libs.logback)
    implementation(libs.apollo.runtime)
    implementation(libs.okhttp.interceptor)
}

// TODO: Activate once server is ready, up and running
// apollo {
//     service("service") {
//         packageName.set("plotting.client")
//     }
// }
