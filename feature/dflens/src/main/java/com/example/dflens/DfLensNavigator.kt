package com.example.dflens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.dfLensGraph(
    mainNavController: NavController
) {
    composable(Destination.DfLens.route) {
        DfLensView()
    }
}

sealed class Destination(val route: String) {
    data object DfLens: Destination("DF_LENS")
}