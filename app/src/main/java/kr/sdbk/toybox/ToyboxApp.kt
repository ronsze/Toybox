package kr.sdbk.toybox

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dflens.dfLensGraph
import com.example.snake.snakeGraph
import kotlinx.serialization.Serializable
import kr.sdbk.timer.timerGraph

@Composable
fun ToyboxApp(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MainDestination.Main,
        modifier = modifier
    ) {
        composable<MainDestination.Main> {
            MainView(navController)
        }

        timerGraph(navController)
        snakeGraph(navController)
        dfLensGraph(navController)
    }
}

sealed interface MainDestination {
    @Serializable
    data object Main
}

enum class Features(@DrawableRes val icon: Int) {
    TIMER(R.drawable.ic_launcher_background),
    WEATHER(R.drawable.ic_launcher_foreground),
    SNAKE(R.drawable.ic_launcher_foreground),
    DF_LENS(R.drawable.ic_launcher_foreground)
}