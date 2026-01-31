package com.example.petinfo.features.countryinfo.data.datasources.remote.maper


import com.example.petinfo.features.countryinfo.data.datasources.remote.model.CountryDto
import com.example.petinfo.features.countryinfo.domain.entites.Country

fun CountryDto.toDomain(): Country {
    return Country(
        name = name.common,
        capital = capital?.firstOrNull() ?: "N/A",
        population = population,
        flagUrl = flags.png,
        language = languages?.values?.firstOrNull() ?: "N/A",
        region = region
    )
}

