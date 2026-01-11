package com.balanmaharaja.weatherapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.balanmaharaja.weatherapp.presentation.components.ErrorView
import com.balanmaharaja.weatherapp.presentation.components.ForecastGrid
import com.balanmaharaja.weatherapp.presentation.viewmodel.WeatherUiState
import com.balanmaharaja.weatherapp.presentation.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onSearchClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onQueryChange("Bangalore")
        viewModel.refresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bangalore – 3 Day Forecast") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = "Search city")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            when (state) {

                WeatherUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is WeatherUiState.Success -> {
                    ForecastGrid(
                        forecast = (state as WeatherUiState.Success).forecast
                    )
                }

                is WeatherUiState.Error -> {
                    ErrorView((state as WeatherUiState.Error).error)
                }

                else -> Unit
            }
        }
    }
}

