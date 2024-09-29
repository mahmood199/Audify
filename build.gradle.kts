// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktlint) apply false
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
        classpath(libs.ktlint.gradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}