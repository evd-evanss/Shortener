plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.shortener.navigation"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    implementation(project(":feature:splash"))
    implementation("androidx.compose.runtime:runtime:1.11.1")
    implementation("androidx.navigation:navigation-compose:2.9.6")
}
