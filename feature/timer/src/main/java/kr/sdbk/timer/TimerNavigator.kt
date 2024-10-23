package kr.sdbk.timer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.sdbk.common.base.Features

fun NavGraphBuilder.timerGraph(
    mainNavController: NavController
) {
    composable(Features.TIMER.name) {
        TimerView()
    }
}