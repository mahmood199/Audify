import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.scrutinizing_the_service"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scrutinizing_the_service"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //LastFM
        buildConfigField("String", "LAST_FM_API_KEY", "\"9f0d1f4e45452f005252775976e4274c\"")
        buildConfigField("String", "SHARED_API_KEY", "\"8e9162fd6f5930584a1adfa06507c3bb\"")
        buildConfigField("String", "LAST_FM_BASE_URL", "\"https://ws.audioscrobbler.com/2.0/\"")

        buildConfigField("String", "SAAVN_BASE_URL", "\"saavn.me/\"")
    }

    signingConfigs {
        register("release") {
            storePassword = "Party"
            keyAlias = "Party2"
            keyPassword = "Password"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    fun Packaging.() {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.constraintlayout)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    implementation("androidx.compose.material3:material3-window-size-class")

    implementation(libs.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.systemuicontroller)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)


    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.service)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Media3 ExoPlayer

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.exoplayer.rtsp)
    implementation(libs.media3.ui)
    implementation(libs.media3.ui.leanback)
    implementation(libs.media3.session)
    implementation(libs.media3.extractor)
    implementation(libs.legacy.support.v4)
    implementation(libs.glide)


    implementation(libs.kotlinx.collections.immutable)

    //KTOR
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.encoding)

    //Brotli
    implementation(libs.brotli.dec)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.paging)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.landscapist.glide)
    implementation(libs.datastore.preferences)
    implementation(libs.compose.audiowaveform)
}