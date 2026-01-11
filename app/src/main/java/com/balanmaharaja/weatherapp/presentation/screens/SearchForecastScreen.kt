package com.balanmaharaja.weatherapp.presentation.screens

import SearchBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun SearchForecastScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val query by viewModel.query.collectAsState()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search City") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            SearchBar(
                query = query,
                onQueryChange = viewModel::onQueryChange,
                onRefresh = viewModel::refresh
            )

            Spacer(Modifier.height(16.dp))

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



