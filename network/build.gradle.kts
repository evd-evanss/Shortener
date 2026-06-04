plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
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
    implementation(project(":observability"))
    implementation("io.ktor:ktor-client-core:3.5.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.5.0")
    implementation("io.ktor:ktor-client-logging:3.5.0")
    implementation("io.ktor:ktor-client-okhttp:3.5.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.5.0")
    implementation("io.insert-koin:koin-core:4.1.1")
}
