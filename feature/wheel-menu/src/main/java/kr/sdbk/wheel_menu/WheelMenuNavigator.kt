package kr.sdbk.wheel_menu

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.sdbk.common.base.Features

fun NavGraphBuilder.wheelMenuGraph() {
    composable(Features.WHEEL_MENU.name) {
        WheelMenuView()
    }
}