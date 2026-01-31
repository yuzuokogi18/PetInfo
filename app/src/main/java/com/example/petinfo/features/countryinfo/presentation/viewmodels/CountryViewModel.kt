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

    private val favorites = mutableListOf<Country>()

    fun searchCountry(name: String) {
        if (name.isBlank()) return

        _uiState.value = CountryUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val country = getCountryUseCase(name.trim().lowercase())

                val finalCountry =
                    if (favorites.any { it.name == country.name }) {
                        country.copy(isFavorite = true)
                    } else country

                _uiState.value = CountryUiState(
                    country = finalCountry,
                    favorites = favorites.toList()
                )
            } catch (e: Exception) {
                _uiState.value = CountryUiState(error = "Country not found")
            }
        }
    }

    fun toggleFavorite() {
        val current = _uiState.value.country ?: return

        if (favorites.any { it.name == current.name }) {
            favorites.removeAll { it.name == current.name }
        } else {
            favorites.add(current.copy(isFavorite = true))
        }

        val updated = current.copy(isFavorite = !current.isFavorite)

        _uiState.value = _uiState.value.copy(
            country = updated,
            favorites = favorites.toList()
        )
    }
    fun toggleFavoriteFromList(country: Country) {
        favorites.removeAll { it.name == country.name }

        val current = _uiState.value.country
        val updatedCurrent =
            if (current?.name == country.name)
                current.copy(isFavorite = false)
            else current

        _uiState.value = _uiState.value.copy(
            country = updatedCurrent,
            favorites = favorites.toList()
        )
    }
    fun setRegion(region: String) {
        _uiState.value = _uiState.value.copy(selectedRegion = region)
    }

    fun setMinPopulation(pop: Int) {
        _uiState.value = _uiState.value.copy(minPopulation = pop)
    }

}
