plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.atry"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.atry"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.play.services.location)
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("androidx.appcompat:appcompat:1.6.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.code.gson:gson:2.8.9")

    //implementation ("com.google.android.youtube:youtube-android-player-api:1.2.2")


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}