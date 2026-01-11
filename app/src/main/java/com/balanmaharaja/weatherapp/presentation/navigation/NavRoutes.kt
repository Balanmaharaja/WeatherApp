package com.balanmaharaja.weatherapp.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Search : NavRoutes("search")
}
