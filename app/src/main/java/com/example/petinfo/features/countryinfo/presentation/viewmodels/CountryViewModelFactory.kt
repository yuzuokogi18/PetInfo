package com.example.petinfo.features.countryinfo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petinfo.features.countryinfo.data.repositories.CountryRepositoryImpl
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountryUseCase

class CountryViewModelFactory(
    private val getCountryUseCase: GetCountryUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryViewModel(getCountryUseCase) as T
    }
}

