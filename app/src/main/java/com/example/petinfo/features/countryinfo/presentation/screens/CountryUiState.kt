package com.example.petinfo.features.countryinfo.presentation.screens

import com.example.petinfo.features.countryinfo.domain.entites.Country


data class CountryUiState(
    val isLoading: Boolean = false,
    val country: Country? = null,
    val favorites: List<Country> = emptyList(),
    val selectedRegion: String = "All",
    val minPopulation: Int = 0,
    val error: String? = null
)



