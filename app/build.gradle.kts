import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use(::load)
    }
}

val sentryDsn = providers.gradleProperty("SENTRY_DSN")
    .orElse(providers.environmentVariable("SENTRY_DSN"))
    .orElse(localProperties.getProperty("SENTRY_DSN", ""))
    .get()

android {
    namespace = "com.shortener"
    compileSdk = 37

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.shortener"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["sentryDsn"] = sentryDsn
    }
}

dependencies {
    implementation(project(":designsystem"))
    implementation(project(":navigation"))
    implementation(project(":observability"))
    implementation(project(":network"))
    implementation(project(":feature:shortener:impl"))

    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.material3:material3:1.5.0-alpha17")
    implementation("androidx.compose.ui:ui:1.11.1")
    implementation("io.insert-koin:koin-android:4.1.1")
    implementation("io.sentry:sentry-android:8.43.0")

    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.11.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.11.1")
}
