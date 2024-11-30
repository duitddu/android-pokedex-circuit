buildscript {

    dependencies {
        classpath(libs.kotlin.gradle.plugin)
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlinKsp) apply false
}