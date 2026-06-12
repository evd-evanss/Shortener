plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.shortener.feature.splash"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    implementation(project(":designsystem"))
    implementation("androidx.compose.animation:animation-core:1.11.1")
    implementation("androidx.compose.foundation:foundation:1.11.1")
    implementation("androidx.compose.material3:material3:1.5.0-alpha17")
    implementation("androidx.compose.runtime:runtime:1.11.1")
    implementation("androidx.compose.ui:ui:1.11.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.11.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.11.1")
}
