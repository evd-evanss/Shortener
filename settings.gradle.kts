pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NubankUrlShortener"
include(":app")
include(":navigation")
include(":observability")
include(":network")
include(":designsystem")
include(":feature:splash")
include(":feature:shortener:domain")
include(":feature:shortener:data")
include(":feature:shortener:presentation")
