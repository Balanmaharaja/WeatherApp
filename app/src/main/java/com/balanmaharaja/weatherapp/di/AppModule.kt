package com.balanmaharaja.weatherapp.di


import android.content.Context
import androidx.room.Room
import com.balanmaharaja.weatherapp.data.api.WeatherApi
import com.balanmaharaja.weatherapp.data.room.ForecastDao
import com.balanmaharaja.weatherapp.data.room.AppDatabase
import com.balanmaharaja.weatherapp.data.room.DailyForecastDao
import com.balanmaharaja.weatherapp.data.room.DailyForecastEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/"


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_db"
        ).build()

    @Provides
    fun provideForecastDao(db: AppDatabase): ForecastDao =
        db.forecastDao()

    @Provides
    fun provideDailyForecastDao(db: AppDatabase): DailyForecastDao =
        db.dailyForecastDao()
}
