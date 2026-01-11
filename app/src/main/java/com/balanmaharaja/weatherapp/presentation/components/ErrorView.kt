package com.balanmaharaja.weatherapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.balanmaharaja.weatherapp.presentation.viewmodel.WeatherError

@Composable
fun ErrorView(error: WeatherError) {
    val message = when (error) {
        WeatherError.NoInternet ->
            "No internet connection. Showing saved data."

        WeatherError.CityNotFound ->
            "City not found. Please check spelling."

        WeatherError.ServerError ->
            "Weather service is temporarily unavailable."

        WeatherError.Unknown ->
            "Something went wrong. Please try again."
    }

    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium
    )
}
