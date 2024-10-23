plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kr.sdbk.wheel_menu"
}

apply(from = "$rootDir/feature-common.gradle")