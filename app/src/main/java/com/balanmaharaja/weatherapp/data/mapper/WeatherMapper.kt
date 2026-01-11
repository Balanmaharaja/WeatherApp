package com.balanmaharaja.weatherapp.data.mapper

import com.balanmaharaja.weatherapp.data.api.ForecastResponse
import com.balanmaharaja.weatherapp.data.api.WeatherApiResponse
import com.balanmaharaja.weatherapp.data.model.DailyForecast
import com.balanmaharaja.weatherapp.data.model.WeatherData
import com.balanmaharaja.weatherapp.data.room.DailyForecastEntity
import com.balanmaharaja.weatherapp.data.room.ForecastEntity
import com.balanmaharaja.weatherapp.util.DateFormatter
import java.text.SimpleDateFormat
import java.util.*

fun WeatherApiResponse.toWeatherData(): WeatherData {
    val date = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        .format(Date(timestamp * 1000))

    return WeatherData(
        city = name,
        country = sys.country,
        date = date,
        temp = main.temp,
        minTemp = main.minTemp,
        maxTemp = main.maxTemp,
        description = weather.firstOrNull()?.description.orEmpty(),
        icon = weather.firstOrNull()?.icon.orEmpty()
    )
}

fun ForecastResponse.toDailyForecast(): List<DailyForecast> {

    return list
        .groupBy {
            DateFormatter.format(it.dt)
        }
        .values
        .take(3)
        .map { day ->
            val item = day.first()
            DailyForecast(
                date = DateFormatter.format(item.dt),
                temp = item.main.temp,
                description = item.weather.first().description,
                icon = item.weather.first().icon
            )
        }
}


fun DailyForecast.toEntity(city: String): DailyForecastEntity {
    return DailyForecastEntity(
        city = city.lowercase(),
        date = date,
        temp = temp,
        description = description,
        icon = icon
    )
}


fun DailyForecastEntity.toDomain(): DailyForecast {
    return DailyForecast(
        date = date,
        temp = temp,
        description = description,
        icon = icon
    )
}




fun ForecastEntity.toWeatherData() = WeatherData(
    city, country, date, temp, minTemp, maxTemp, description, icon
)

fun WeatherData.toEntity() = ForecastEntity(
    city, country, date, temp, minTemp, maxTemp, description, icon
)
