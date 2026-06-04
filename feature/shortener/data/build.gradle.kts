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
    implementation(project(":network"))
    implementation(project(":observability"))
    implementation(project(":feature:shortener:domain"))
    implementation("io.insert-koin:koin-core:4.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
}
