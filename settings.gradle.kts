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
rootProject.name = "The Movie DB"
include(":app")
include(":domain")
include(":data")
include(":datasource")
include(":presentation")
include(":ui")
include(":coroutine-test")
