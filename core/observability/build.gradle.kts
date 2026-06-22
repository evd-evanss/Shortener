plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(17)

    sourceSets {
        main {
            kotlin.srcDir("src/main/java")
        }
    }
}

dependencies {
    implementation("io.insert-koin:koin-core:4.1.1")
    implementation("io.sentry:sentry:8.43.0")

    testImplementation(kotlin("test"))
}
