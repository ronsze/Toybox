plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kr.sdbk.dflens"
}

apply(from = "$rootDir/feature-common.gradle")