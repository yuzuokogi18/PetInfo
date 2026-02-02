package com.example.petinfo.core.network

import com.example.petinfo.features.countryinfo.data.datasources.remote.model.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("name/{country}")
    suspend fun getCountryByName(
        @Path("country") country: String
    ): List<CountryDto>

    @GET("region/{region}")
    suspend fun getCountriesByRegion(
        @Path("region") region: String
    ): List<CountryDto>
}
