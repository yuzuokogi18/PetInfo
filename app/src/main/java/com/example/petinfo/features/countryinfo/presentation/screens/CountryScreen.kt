package com.example.petinfo.features.countryinfo.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petinfo.features.countryinfo.presentation.components.CountryCard
import com.example.petinfo.features.countryinfo.presentation.viewmodels.*
import com.example.petinfo.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(factory: CountryViewModelFactory) {

    val viewModel: CountryViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    Scaffold(
        containerColor = LightBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CountryInfo", color = TextPrimary) }
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
                Text("❤️ Países favoritos", fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(8.dp))

                state.favorites.forEach { fav ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground)
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

                            Spacer(Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(fav.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text(fav.capital, color = TextSecondary)
                            }

                            IconButton(
                                onClick = {
                                    viewModel.toggleFavoriteFromList(fav)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Quitar",
                                    tint = FavoriteRed
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("mexico", color = TextSecondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = { viewModel.searchCountry(query) },
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                    ) {
                        Icon(Icons.Default.Search, null, tint = Color.White)
                        Spacer(Modifier.width(4.dp))
                        Text("Buscar", color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            when {
                state.isLoading -> CircularProgressIndicator()

                state.error != null -> Text(state.error!!, color = FavoriteRed)

                state.country != null -> {
                    CountryCard(
                        country = state.country!!,
                        onFavorite = { viewModel.toggleFavorite() }
                    )
                }
            }
        }
    }
}
