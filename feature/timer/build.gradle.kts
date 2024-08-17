plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kr.sdbk.timer"
}

apply(from = "$rootDir/feature-common.gradle")