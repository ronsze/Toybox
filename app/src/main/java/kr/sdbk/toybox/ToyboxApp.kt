package kr.sdbk.toybox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

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
    }
}

sealed interface MainDestination {
    @Serializable
    data object Main
}