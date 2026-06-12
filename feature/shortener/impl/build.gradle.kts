plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.shortener.feature.shortener.impl"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":feature:shortener:api"))
    implementation(project(":designsystem"))
    implementation(project(":observability"))
    implementation(project(":network"))
    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.foundation:foundation:1.11.1")
    implementation("androidx.compose.material3:material3:1.5.0-alpha17")
    implementation("androidx.compose.runtime:runtime:1.11.1")
    implementation("androidx.compose.ui:ui:1.11.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.11.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose-android:2.10.0")
    implementation("io.insert-koin:koin-android:4.1.1")
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("app.cash.turbine:turbine:1.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.11.1")
}
