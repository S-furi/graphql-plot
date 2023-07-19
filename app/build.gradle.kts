 plugins {
     id("plotting.kotlin-js-conventions")
 }

 dependencies {
     implementation(libs.letsplot.js)
     testImplementation(libs.kotlin.test.js)
     implementation(libs.kotlin.coroutines.core)
 }