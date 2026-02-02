package com.example.petinfo.features.countryinfo.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petinfo.features.countryinfo.presentation.components.CountryCard
import com.example.petinfo.features.countryinfo.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(factory: CountryViewModelFactory) {

    val viewModel: CountryViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    val regions = listOf("Africa", "Americas", "Asia", "Europe", "Oceania")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "CountryInfo",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            if (state.favorites.isNotEmpty()) {
                Text(
                    "❤️ Países favoritos",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(12.dp))

                state.favorites.forEach { fav ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = fav.flagUrl,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(fav.name, fontWeight = FontWeight.Bold)
                                Text(fav.capital, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            IconButton(
                                onClick = {
                                    viewModel.toggleFavorite(fav)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Quitar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text("mexico", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(Modifier.width(10.dp))

                    Button(
                        onClick = { viewModel.searchCountry(query) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Search, null)
                        Spacer(Modifier.width(6.dp))
                        Text("Buscar")
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Región: ${state.selectedRegion}")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    regions.forEach { region ->
                        DropdownMenuItem(
                            text = { Text(region) },
                            onClick = {
                                expanded = false
                                viewModel.searchByRegion(region)
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Población mínima: ${state.minPopulation}",
                color = MaterialTheme.colorScheme.onBackground
            )

            Slider(
                value = state.minPopulation.toFloat(),
                onValueChange = {
                    viewModel.setMinPopulation(it.toInt())
                },
                valueRange = 0f..200_000_000f,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(20.dp))

            when {
                state.isLoading -> CircularProgressIndicator()

                state.error != null -> Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error
                )

                state.countryList.isNotEmpty() -> {
                    LazyColumn {
                        items(state.countryList) { country ->
                            CountryCard(
                                country = country,

                                onFavorite = { viewModel.toggleFavorite(country) }

                            )
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }

                state.country != null -> {
                    CountryCard(
                        country = state.country!!,
                        onFavorite = { viewModel.toggleFavorite(state.country!!) }
                    )
                }
            }
        }
    }
}
