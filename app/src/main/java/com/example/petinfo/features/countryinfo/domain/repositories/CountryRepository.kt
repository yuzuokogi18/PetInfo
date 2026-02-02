package com.example.petinfo.features.countryinfo.domain.repositories

import com.example.petinfo.features.countryinfo.domain.entites.Country

interface CountryRepository {
    suspend fun getCountry(name: String): Country

    suspend fun getCountriesByRegion(region: String): List<Country>
}
