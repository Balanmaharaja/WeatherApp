package com.balanmaharaja.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balanmaharaja.weatherapp.data.model.DailyForecast
import com.balanmaharaja.weatherapp.data.model.WeatherData
import com.balanmaharaja.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {


    init {
        viewModelScope.launch {
            repository.clearOldForecast()
        }
    }


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // 🔹 UI state
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        observeSearch()
    }


    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _query
                .debounce(600)
                .distinctUntilChanged()
                .filter { it.length >= 2 }
                .collectLatest { city ->
                    fetchWeather(city)
                }
        }
    }

    fun onQueryChange(text: String) {
        _query.value = text
    }

    fun refresh() {
        if (_query.value.isNotBlank()) {
            fetchWeather(_query.value)
        }
    }

    private fun fetchWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading


            val todayResult = runCatching {
                repository.getWeather(city)
            }

            if (todayResult.isFailure) {
                _uiState.value = WeatherUiState.Error(
                    todayResult.exceptionOrNull()?.toWeatherError()
                        ?: WeatherError.Unknown
                )
                return@launch
            }

            val today = todayResult.getOrThrow()


            val forecast = runCatching {
                repository.getThreeDayForecast(city)
            }.getOrDefault(emptyList())

            _uiState.value = WeatherUiState.Success(
                today = today,
                forecast = forecast
            )
        }
    }
}



fun Throwable.toWeatherError(): WeatherError =
    when (this) {
        is java.net.UnknownHostException,
        is java.net.SocketTimeoutException ->
            WeatherError.NoInternet

        is retrofit2.HttpException ->
            if (code() == 404)
                WeatherError.CityNotFound
            else
                WeatherError.ServerError

        else -> WeatherError.Unknown
    }



sealed class WeatherUiState {

    object Idle : WeatherUiState()
    object Loading : WeatherUiState()

    data class Success(
        val today: WeatherData,
        val forecast: List<DailyForecast>
    ) : WeatherUiState()

    data class Error(
        val error: WeatherError
    ) : WeatherUiState()
}



sealed class WeatherError {
    object NoInternet : WeatherError()
    object CityNotFound : WeatherError()
    object ServerError : WeatherError()
    object Unknown : WeatherError()
}
