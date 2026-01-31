package com.example.petinfo.core.di

import android.content.Context
import com.example.petinfo.core.network.CountriesApi
import com.example.petinfo.features.countryinfo.data.repositories.CountryRepositoryImpl
import com.example.petinfo.features.countryinfo.domain.repositories.CountryRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countriesApi: CountriesApi by lazy {
        retrofit.create(CountriesApi::class.java)
    }

    val countryRepository: CountryRepository by lazy {
        CountryRepositoryImpl(countriesApi)
    }
}
