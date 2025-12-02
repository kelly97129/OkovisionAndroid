plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "com.okovision.android"

    // Garde tes valeurs actuelles si elles sont déjà définies ailleurs
    compileSdk = 34

    defaultConfig {
        applicationId = "com.okovision.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // --- Compose (BOM recommandé) ---
    val composeBom = platform("androidx.compose:compose-bom:2025.10.01") // doc Android :contentReference[oaicite:2]{index=2}
    implementation("androidx.compose:compose-bom:2025.11.01")
    androidTestImplementation("androidx.compose:compose-bom:2025.11.01")

    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose")

    // --- Thèmes XML Material3 (Theme.Material3.*) ---
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.appcompat:appcompat:1.7.1")

    // --- Coroutines / Lifecycle ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // --- WorkManager (imports androidx.work.* / Configuration.Provider) ---
    implementation("androidx.work:work-runtime-ktx:2.11.0")

    // --- DataStore Preferences (imports androidx.datastore.*) ---
    implementation("androidx.datastore:datastore-preferences:1.2.0")

    // --- Room (imports androidx.room.*) ---
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")

    // --- OKHttp (imports okhttp3.*) ---
    implementation("com.squareup.okhttp3:okhttp:5.3.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")

    // --- Retrofit + converter Kotlinx Serialization ---
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // --- MQTT (imports org.eclipse.paho.client.mqttv3.*) ---
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")

    // --- MPAndroidChart (optionnel) ---
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
