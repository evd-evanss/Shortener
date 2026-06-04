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
include(":observability")
include(":network")
include(":designsystem")
include(":feature:shortener:domain")
include(":feature:shortener:data")
include(":feature:shortener:presentation")
