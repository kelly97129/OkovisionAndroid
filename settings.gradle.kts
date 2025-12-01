pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // Pour le plugin MPAndroidChart (JitPack)
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    // Tu peux garder FAIL_ON_PROJECT_REPOS si tu veux quelque chose de strict
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // IMPORTANT : dépôt JitPack pour MPAndroidChart
        maven("https://jitpack.io")
    }
}

rootProject.name = "OkovisionAndroid"
include(":app")
