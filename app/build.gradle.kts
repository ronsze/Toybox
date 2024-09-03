plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlinx-serialization")
}

android {
    namespace = "kr.sdbk.toybox"

    defaultConfig {
        applicationId = "kr.sdbk.toybox"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

apply(from = "$rootDir/feature-common.gradle")

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(project(":feature:timer"))
    implementation(project(":feature:weather"))
    implementation(project(":feature:snake"))
    implementation(project(":feature:dflens"))
}