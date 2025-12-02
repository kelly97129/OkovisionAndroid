plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false

    // Requis avec Kotlin 2.x quand compose = true
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false

    // Pour kotlinx.serialization (imports `kotlinx.serialization.*`)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21" apply false
}
