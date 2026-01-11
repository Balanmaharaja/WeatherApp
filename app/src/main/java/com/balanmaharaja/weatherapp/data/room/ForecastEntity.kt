package com.balanmaharaja.weatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey
    val city: String,
    val country: String,
    val date: String,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val description: String,
    val icon: String
)



