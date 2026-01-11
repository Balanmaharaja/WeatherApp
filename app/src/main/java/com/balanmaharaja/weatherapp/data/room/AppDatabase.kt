package com.balanmaharaja.weatherapp.data.room


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ForecastEntity::class, DailyForecastEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
    abstract fun dailyForecastDao(): DailyForecastDao

}
