package kr.sdbk.toybox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import kr.sdbk.timer.timerGraph
import kr.sdbk.wheel_menu.wheelMenuGraph

@Composable
fun ToyboxApp(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Main,
        modifier = modifier
    ) {
        composable<Main> {
            MainView(navController)
        }

        timerGraph(navController)

        wheelMenuGraph()
    }
}

@Serializable
data object Main