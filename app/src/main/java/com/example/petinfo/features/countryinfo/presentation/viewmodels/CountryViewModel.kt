package com.example.petinfo.features.countryinfo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountryUseCase
import com.example.petinfo.features.countryinfo.presentation.screens.CountryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryViewModel(
    private val getCountryUseCase: GetCountryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryUiState())
    val uiState = _uiState.asStateFlow()

    private var favoriteCountry: Country? = null

    fun searchCountry(name: String) {
        if (name.isBlank()) return

        _uiState.value = CountryUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val country = getCountryUseCase(name.trim().lowercase())

                val finalCountry =
                    if (favoriteCountry?.name == country.name) {
                        country.copy(isFavorite = true)
                    } else country

                _uiState.value = CountryUiState(country = finalCountry)
            } catch (e: Exception) {
                _uiState.value = CountryUiState(error = "Country not found")
            }
        }
    }

    fun toggleFavorite() {
        val current = _uiState.value.country ?: return

        val updated = current.copy(isFavorite = !current.isFavorite)
        favoriteCountry = if (updated.isFavorite) updated else null

        _uiState.value = _uiState.value.copy(country = updated)
    }
    fun togglePopulationFilter() {
        val current = _uiState.value
        _uiState.value = current.copy(
            showOnlyHighPopulation = !current.showOnlyHighPopulation
        )
    }

}
