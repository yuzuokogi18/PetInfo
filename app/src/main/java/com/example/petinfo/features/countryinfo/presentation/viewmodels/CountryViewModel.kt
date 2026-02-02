package com.example.petinfo.features.countryinfo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountryUseCase
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountriesByRegionUseCase
import com.example.petinfo.features.countryinfo.presentation.screens.CountryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryViewModel(
    private val getCountryUseCase: GetCountryUseCase,
    private val getCountriesByRegion: GetCountriesByRegionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryUiState())
    val uiState = _uiState.asStateFlow()

    private val favorites = mutableListOf<Country>()
    private var lastRegionList: List<Country> = emptyList()

    fun searchCountry(name: String) {
        if (name.isBlank()) return

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = getCountryUseCase(name.trim())

            result.fold(
                onSuccess = { country ->
                    val finalCountry =
                        if (favorites.any { it.name == country.name })
                            country.copy(isFavorite = true)
                        else country

                    _uiState.value = _uiState.value.copy(
                        country = finalCountry,
                        isLoading = false
                    )
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(
                        error = "Country not found",
                        isLoading = false
                    )
                }
            )
        }
    }

    // -------- BUSCAR POR REGIÓN --------
    fun searchByRegion(region: String) {
        val cleanRegion = region.trim()

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = getCountriesByRegion(cleanRegion)

            result.fold(
                onSuccess = { list ->
                    lastRegionList = list
                    val filtered = applyPopulationFilter(list)

                    _uiState.value = _uiState.value.copy(
                        countryList = filtered,
                        selectedRegion = cleanRegion,
                        isLoading = false
                    )
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(
                        error = "No se encontraron países",
                        isLoading = false
                    )
                }
            )
        }
    }

    // -------- FILTRO POBLACIÓN --------
    fun setMinPopulation(pop: Int) {
        val filtered = lastRegionList.filter {
            it.population >= pop
        }

        _uiState.value = _uiState.value.copy(
            minPopulation = pop,
            countryList = filtered
        )
    }

    private fun applyPopulationFilter(list: List<Country>): List<Country> {
        val min = _uiState.value.minPopulation
        return list.filter { it.population >= min }
    }

    // -------- FAVORITOS (LÓGICA SIMPLE) --------
    fun toggleFavorite(country: Country) {

        val exists = favorites.any { it.name == country.name }

        if (exists) {
            favorites.removeAll { it.name == country.name }
        } else {
            favorites.add(country.copy(isFavorite = true))
        }

        val current = _uiState.value.country
        val updatedCurrent =
            if (current != null && current.name == country.name)
                current.copy(isFavorite = !current.isFavorite)
            else current

        val updatedList = _uiState.value.countryList.map { item ->
            if (item.name == country.name)
                item.copy(isFavorite = !item.isFavorite)
            else item
        }

        _uiState.value = _uiState.value.copy(
            country = updatedCurrent,
            countryList = updatedList,
            favorites = favorites.toList()
        )
    }
}
