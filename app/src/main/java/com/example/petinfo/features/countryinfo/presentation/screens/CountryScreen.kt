package com.example.petinfo.features.countryinfo.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petinfo.features.countryinfo.presentation.viewmodels.CountryViewModel
import com.example.petinfo.features.countryinfo.presentation.viewmodels.CountryViewModelFactory
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(factory: CountryViewModelFactory) {

    val viewModel: CountryViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Country Info") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search country") }
            )

            Button(
                onClick = { viewModel.searchCountry(query) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> CircularProgressIndicator()

                state.error != null -> Text(state.error!!)

                state.country != null -> {
                    AsyncImage(
                        model = state.country!!.flagUrl,
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                    Text(state.country!!.name, style = MaterialTheme.typography.titleLarge)
                    Text("Capital: ${state.country!!.capital}")
                    Text("Population: ${state.country!!.population}")
                    Text("Language: ${state.country!!.language}")
                }
            }
        }
    }
}
