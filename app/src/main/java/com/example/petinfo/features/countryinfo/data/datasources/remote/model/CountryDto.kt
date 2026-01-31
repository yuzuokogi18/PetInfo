package com.example.petinfo.features.countryinfo.data.datasources.remote.model


data class CountryDto(
    val name: NameDto,
    val capital: List<String>?,
    val population: Long,
    val flags: FlagsDto,
    val languages: Map<String, String>?,
    val region: String
)


data class NameDto(
    val common: String
)

data class FlagsDto(
    val png: String
)
