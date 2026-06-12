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

rootProject.name = "Shortener"
include(":app")
include(":navigation")
include(":observability")
include(":network")
include(":designsystem")
include(":feature:splash")
include(":feature:shortener:api")
include(":feature:shortener:impl")
