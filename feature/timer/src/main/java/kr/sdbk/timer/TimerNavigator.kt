package kr.sdbk.timer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.timerGraph(
    mainNavController: NavController
) {
    composable(Destination.Timer.route) {
        TimerView()
    }
}

sealed class Destination(val route: String) {
    data object Timer: Destination("TIMER")
}