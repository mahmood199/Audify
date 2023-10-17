// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0-alpha07" apply false
    id("com.android.library") version "8.3.0-alpha07" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.41" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}