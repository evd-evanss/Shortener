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
include(":core:observability")
include(":core:network")
include(":core:designsystem")
include(":navigation:api")
include(":feature:splash")
include(":feature:share:api")
include(":feature:share:impl")
include(":feature:shortener:api")
include(":feature:shortener:impl")
