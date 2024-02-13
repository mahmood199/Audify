// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0-alpha08" apply false
    id("com.android.library") version "8.4.0-alpha08" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.android.test") version "8.4.0-alpha08" apply false
    id("androidx.baselineprofile") version "1.2.3" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    alias(libs.plugins.paparazzi) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlinx.coroutines.test.jvm)
        classpath(libs.paparazzi.gradle.plugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}