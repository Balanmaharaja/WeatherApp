package com.balanmaharaja.weatherapp.data.model

data class WeatherData(
    val city: String,
    val country: String,
    val date: String,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val description: String,
    val icon: String
)


data class DailyForecast(
    val date: String,
    val temp: Double,
    val description: String,
    val icon: String
)

