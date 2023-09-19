plugins {
    kotlin("multiplatform")
    id("com.android.library")

    kotlin("plugin.serialization") version embeddedKotlinVersion // "1.9.10"
    id("app.cash.sqldelight") // version "2.0.0"

}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    val coroutinesVersion = "1.7.1"
    val ktorVersion = "2.3.2"
    val sqlDelightVersion = "2.0.0"
    val dateTimeVersion = "0.4.0"

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                // todo: keep common dependencies over here!
                // kotlin coroutine
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                // ktor networking library
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                // sql-delight runtime only
                implementation("app.cash.sqldelight:runtime:$sqlDelightVersion")
                // kotlin datetime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
            }
        }


        val androidMain by getting {
            dependencies {
                // ktor android
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                // sql-delight android driver
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            }
        }

        val iosMain by getting {
            dependencies {
                // ktor ios
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                // sql-delight native driver
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.example.kmmapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}