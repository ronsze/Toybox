package kr.sdbk.weather

import androidx.compose.runtime.Composable

@Composable
fun WeatherView(
    popupBackstack: () -> Unit,
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

}