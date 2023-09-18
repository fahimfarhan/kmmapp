plugins {
    kotlin("multiplatform")
    id("com.android.library")

    kotlin("plugin.serialization") version embeddedKotlinVersion // "1.9.10"

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

    val ktorVersion = "2.3.2"

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                // todo: keep common dependencies over here!
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")            }
        }


        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
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