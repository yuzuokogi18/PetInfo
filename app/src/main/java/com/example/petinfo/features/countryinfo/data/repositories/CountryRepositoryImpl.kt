package com.example.petinfo.features.countryinfo.data.repositories

import com.example.petinfo.core.network.CountriesApi
import com.example.petinfo.features.countryinfo.data.datasources.remote.maper.toDomain
import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.features.countryinfo.domain.repositories.CountryRepository

class CountryRepositoryImpl(
    private val api: CountriesApi
) : CountryRepository {

    override suspend fun getCountry(name: String): Country {
        return api.getCountryByName(name)[0].toDomain()
    }

    override suspend fun getCountriesByRegion(region: String): List<Country> {
        return api.getCountriesByRegion(region).map { it.toDomain() }
    }
}
