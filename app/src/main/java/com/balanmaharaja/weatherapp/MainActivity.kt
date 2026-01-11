package com.balanmaharaja.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.balanmaharaja.weatherapp.presentation.navigation.NavRoutes
import com.balanmaharaja.weatherapp.presentation.screens.HomeScreen
import com.balanmaharaja.weatherapp.presentation.screens.SearchForecastScreen
import com.balanmaharaja.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.Home.route
                ) {

                    composable(NavRoutes.Home.route) {
                        HomeScreen(
                            onSearchClick = {
                                navController.navigate(NavRoutes.Search.route)
                            }
                        )
                    }

                    composable(NavRoutes.Search.route) {
                        SearchForecastScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
