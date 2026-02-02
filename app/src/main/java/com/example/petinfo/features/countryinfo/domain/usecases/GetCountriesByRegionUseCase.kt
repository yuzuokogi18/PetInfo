package com.example.petinfo.features.countryinfo.domain.usecases

import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.features.countryinfo.domain.repositories.CountryRepository

class GetCountriesByRegionUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(region: String): Result<List<Country>> {
        return try {
            val list = repository.getCountriesByRegion(region)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
