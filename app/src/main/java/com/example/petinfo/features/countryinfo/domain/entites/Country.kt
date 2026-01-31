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
    val populationLabel: String
        get() = when {
            population > 100_000_000 -> "País con población alta"
            population < 10_000_000 -> "País con población baja"
            else -> "País con población media"
        }

    val populationReadable: String
        get() = "%,d".format(population)
}

