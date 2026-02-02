package com.example.petinfo.features.countryinfo.domain.entites

data class Country(
    val name: String,
    val capital: String,
    val population: Long,
    val flagUrl: String,
    val language: String,
    val region: String,
    val isFavorite: Boolean = false
) {
    val populationReadable: String
        get() = "%,d".format(population)
}
