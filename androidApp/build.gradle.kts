plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.kmmapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.kmmapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = false
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    // kmm android-ios shared module
    implementation(project(":shared"))
    // AppCompatActivity
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Android compose
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")
}