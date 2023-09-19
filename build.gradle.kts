plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.1").apply(false)
    id("com.android.library").version("8.1.1").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
        // classpath("com.android.tools.build:gradle:8.0.2")
        classpath("app.cash.sqldelight:gradle-plugin:2.0.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}