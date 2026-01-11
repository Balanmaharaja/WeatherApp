package com.balanmaharaja.weatherapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast  WHERE city = :city COLLATE NOCASE LIMIT 1")
    suspend fun getForecast(city: String): ForecastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ForecastEntity)
}
