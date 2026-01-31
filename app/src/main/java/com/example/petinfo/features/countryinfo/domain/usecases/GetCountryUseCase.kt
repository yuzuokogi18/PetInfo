package com.example.petinfo.features.countryinfo.domain.usecases

import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.features.countryinfo.domain.repositories.CountryRepository

class GetCountryUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(name: String): Country {
        return repository.getCountry(name)
    }
}
