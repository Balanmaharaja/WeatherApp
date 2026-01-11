package com.balanmaharaja.weatherapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyForecastDao {

    @Query("SELECT * FROM daily_forecast WHERE city = :city COLLATE NOCASE")
    suspend fun getForecast(city: String): List<DailyForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast WHERE city = :city COLLATE NOCASE")
    suspend fun deleteByCity(city: String)

    @Query("DELETE FROM daily_forecast WHERE date < :todayDate")
    suspend fun deleteOldForecast(todayDate: String)
}
