plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
//    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.example.core_data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        //LastFM
        buildConfigField("String", "LAST_FM_API_KEY", "\"9f0d1f4e45452f005252775976e4274c\"")
        buildConfigField("String", "SHARED_API_KEY", "\"8e9162fd6f5930584a1adfa06507c3bb\"")
        buildConfigField("String", "LAST_FM_BASE_URL", "\"https://ws.audioscrobbler.com/2.0/\"")

        buildConfigField("String", "SAAVN_BASE_URL", "\"saavn.me/\"")
        buildConfigField("String", "VERSION_NAME", "\"1.0\"")
/*
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
*/
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }

    packaging {
        resources {
            exclude("/META-INF/AL2.0")
            exclude("/META-INF/LGPL2.1")
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.paging)

    implementation(libs.datastore.preferences)

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

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)


    // Media3 ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.exoplayer.rtsp)
    implementation(libs.media3.ui)
    implementation(libs.media3.ui.leanback)
    implementation(libs.media3.session)
    implementation(libs.media3.extractor)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.remote.config)


    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.common)
    androidTestImplementation(libs.androidx.core.runtime)

    testImplementation(libs.room.testing)
//    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.room.testing)
    //testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.mockito.android)

    testImplementation(libs.byte.buddy.agent)

}