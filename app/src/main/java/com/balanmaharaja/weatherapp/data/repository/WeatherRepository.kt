package com.balanmaharaja.weatherapp.data.repository


import com.balanmaharaja.weatherapp.BuildConfig
import com.balanmaharaja.weatherapp.data.api.WeatherApi
import com.balanmaharaja.weatherapp.data.mapper.toDailyForecast
import com.balanmaharaja.weatherapp.data.mapper.toDomain
import com.balanmaharaja.weatherapp.data.mapper.toEntity
import com.balanmaharaja.weatherapp.data.mapper.toWeatherData
import com.balanmaharaja.weatherapp.data.model.DailyForecast
import com.balanmaharaja.weatherapp.data.model.WeatherData
import com.balanmaharaja.weatherapp.data.room.DailyForecastDao
import com.balanmaharaja.weatherapp.data.room.ForecastDao
import com.balanmaharaja.weatherapp.util.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: ForecastDao,
    private val dailyForecastDao: DailyForecastDao
) {

  val  apiKey = BuildConfig.WEATHER_API_KEY

    suspend fun getWeather(city: String): WeatherData =
        withContext(Dispatchers.IO) {
            try {

                val response = api.getCurrentWeather(
                    city = city,
                    units = "metric",
                    apiKey = apiKey
                )

                val weatherData = response.toWeatherData()


                dao.insert(weatherData.toEntity())

                weatherData
            } catch (e: Exception) {
                dao.getForecast(city)?.toWeatherData()
                    ?: throw e
            }
        }

    suspend fun getThreeDayForecast(city: String): List<DailyForecast> =
        withContext(Dispatchers.IO) {
            try {

                val response = api.getForecast(
                    city = city,
                    units = "metric",
                    apiKey = apiKey
                )

                val forecast = response.toDailyForecast()

                dailyForecastDao.deleteByCity(city.lowercase())


                dailyForecastDao.insertAll(
                    forecast.map { it.toEntity(city) }
                )

                forecast
            } catch (e: Exception) {

                dailyForecastDao
                    .getForecast(city.lowercase().trim())
                    .map { it.toDomain() }
            }
        }

    suspend fun clearOldForecast() {
        withContext(Dispatchers.IO) {
            dailyForecastDao.deleteOldForecast(
                DateFormatter.todayForDb()
            )
        }
    }

}


