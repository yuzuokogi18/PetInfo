package com.example.petinfo.features.countryinfo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountryUseCase
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountriesByRegionUseCase

class CountryViewModelFactory(
    private val getCountryUseCase: GetCountryUseCase,
    private val getCountriesByRegionUseCase: GetCountriesByRegionUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryViewModel(
            getCountryUseCase,
            getCountriesByRegionUseCase
        ) as T
    }
}
