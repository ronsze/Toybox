package com.example.snake

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.snakeGraph(
    mainNavController: NavController
) {
    composable(Destination.Snake.route) {
        SnakeView()
    }
}

sealed class Destination(val route: String) {
    data object Snake: Destination("SNAKE")
}