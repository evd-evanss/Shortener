plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.shortener.feature.share.api"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    api(project(":navigation:api"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.10.0")
}
