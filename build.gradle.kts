buildscript {
    repositories {
        maven { url = uri("https://storage.googleapis.com/r8-releases/raw") }
    }
    dependencies {
        classpath(libs.android.shortcut.gradle)
        // Pin a newer R8 that understands Kotlin 2.2 metadata (AGP 8.9.1 bundles an
        // older R8 that emits "An error occurred when parsing kotlin metadata" warnings).
        classpath("com.android.tools.r8:r8:8.10.21")
    }
}

plugins {
    alias(kotlinx.plugins.serialization) apply false
    alias(libs.plugins.aboutLibraries) apply false
    alias(libs.plugins.moko) apply false
    alias(libs.plugins.sqldelight) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
