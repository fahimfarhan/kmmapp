# KMM App Demo

* Initially, creating kmm project gives you a hello world project. It has compose-android app, and an ios app.
* Since our existing codebase (b2b, b2c etc) don't use compose, but xml, fragments, etc, we need to  convert this compose app into a normal app
* In `androidApp/build.gradle.kts`, make the following changes:
```kotlin
android {
    buildFeatures {
        compose = false
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // kmm android-ios shared module
    implementation(project(":shared"))
    // AppCompatActivity
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.appcompat:appcompat-resources:1.6.1")
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
```
In `res/values/styles.xml`, make the change: `<style name="AppTheme" parent="Theme.AppCompat.DayNight.NoActionBar" />`
Finally, create a `res/layout/activity_main.xml`, and make some changes in `MainActivity.kt`. Remove compose related codes.

* Warning: SqlDelight has changed package name, so use `app.cash.sqldelight`, not the old one.
* 

## References
* [Integrate with ktor and sql-delight](https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html#add-dependencies-to-the-multiplatform-library)
* [multiplatform-mobile-getting-started](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)
* [jetpack compose](https://developer.android.com/jetpack/compose/setup)
* [jetpack compose tutorial](https://developer.android.com/jetpack/compose/tutorial)
* [SqlDelight](https://cashapp.github.io/sqldelight/2.0.0/android_sqlite/)