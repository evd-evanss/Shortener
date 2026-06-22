plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.shortener.navigation.api"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    api("androidx.compose.runtime:runtime:1.11.1")
    api("androidx.navigation3:navigation3-runtime:1.1.3")
}
