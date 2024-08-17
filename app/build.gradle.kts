plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kr.sdbk.toybox"

    defaultConfig {
        applicationId = "kr.sdbk.toybox"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

apply(from = "$rootDir/feature-common.gradle")

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(project(":feature:weather"))
}