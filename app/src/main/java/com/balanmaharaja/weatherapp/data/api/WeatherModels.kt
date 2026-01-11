package com.balanmaharaja.weatherapp.data.api

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("main")
    val main: Main,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("dt")
    val timestamp: Long
)

data class Sys(
    @SerializedName("country")
    val country: String
)

data class Main(
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("temp_min")
    val minTemp: Double,

    @SerializedName("temp_max")
    val maxTemp: Double
)

data class Weather(
    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)


data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastItem>
)

data class ForecastItem(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>
)





