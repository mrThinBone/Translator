buildscript {
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    //alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}