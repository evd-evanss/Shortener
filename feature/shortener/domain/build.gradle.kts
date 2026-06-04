plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(17)

    sourceSets {
        main {
            kotlin.srcDir("src/main/java")
        }
        test {
            kotlin.srcDir("src/test/java")
        }
    }
}

dependencies {
    implementation("io.insert-koin:koin-core:4.1.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
}
