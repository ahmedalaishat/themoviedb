@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.alaishat.ahmed.themoviedb.datasource"
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {
    implementation(project(":data"))

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Ktor
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.mock)
    implementation(libs.ktor.serialization.kotlinx.json)

    // SQL Delight
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.paging)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.kotlinx.coroutines)
    testImplementation(project(":coroutine-test"))
}

sqldelight {
    databases {
        create("TheMovieDBDatabase") {
            packageName.set("com.alaishat.ahmed.themoviedb")
        }
    }
}