plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central for other dependencies
    }

    dependencies {
        // Android Gradle Plugin version 8.0.0 or compatible version
        classpath (libs.gradle)  // Ensure this matches your setup

        // Google Services Plugin version 4.4.2 or update it if required
        classpath (libs.google.services)
    }
}
