plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.skydiver.audify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.skydiver.audify"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.audify.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //LastFM
        buildConfigField("String", "LAST_FM_API_KEY", "\"9f0d1f4e45452f005252775976e4274c\"")
        buildConfigField("String", "SHARED_API_KEY", "\"8e9162fd6f5930584a1adfa06507c3bb\"")
        buildConfigField("String", "LAST_FM_BASE_URL", "\"https://ws.audioscrobbler.com/2.0/\"")

        buildConfigField("String", "SAAVN_BASE_URL", "\"saavn.me/\"")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    signingConfigs {
        create("release") {
            storePassword = "Mahmood"
            keyAlias = "key0"
            storeFile = file("./music_player_key_store.key")
            keyPassword = "Mahmood"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=androidx.compose.material.ExperimentalMaterialApi")
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            jniLibs.excludes.add("/META-INF/AL2.0")
            jniLibs.excludes.add("/META-INF/LGPL2.1")
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {

    implementation(project(":data"))

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
    implementation(libs.compose.material3.window.size)
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.system.ui.controller)
    implementation(libs.compose.runtime)
    //implementation("androidx.compose.material:material")
    implementation(libs.compose.foundation)


    implementation(libs.androidx.profileinstaller)


    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.service)
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.testing)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)



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
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.encoding)
    implementation(libs.slf4j.simple)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.paging)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.landscapist.glide)
    implementation(libs.landscapist.glide.transformation)
    implementation(libs.datastore.preferences)
    implementation(libs.compose.audiowaveform)

    implementation(libs.palette.ktx)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    implementation(libs.compose.constraint.layout)

    implementation(libs.prdownloader)

    implementation(libs.perfsuite)

    implementation(libs.lottie.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.performance)
    implementation(libs.firebase.remote.config)


    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test.jvm)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.mock.webserver)

}