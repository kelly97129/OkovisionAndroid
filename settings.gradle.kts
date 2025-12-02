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
        // nécessaire si tu utilises MPAndroidChart (com.github.PhilJay:MPAndroidChart)
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "OkovisionAndroid"
include(":app")
