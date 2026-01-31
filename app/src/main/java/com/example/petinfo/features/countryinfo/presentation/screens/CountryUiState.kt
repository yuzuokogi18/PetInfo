package com.example.petinfo.features.countryinfo.presentation.screens

import com.example.petinfo.features.countryinfo.domain.entites.Country


data class CountryUiState(
    val isLoading: Boolean = false,
    val country: Country? = null,
    val error: String? = null,
    val showOnlyHighPopulation: Boolean = false
)

