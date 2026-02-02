package com.example.petinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.petinfo.core.di.AppContainer
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountryUseCase
import com.example.petinfo.features.countryinfo.domain.usecases.GetCountriesByRegionUseCase
import com.example.petinfo.features.countryinfo.presentation.screens.CountryScreen
import com.example.petinfo.features.countryinfo.presentation.viewmodels.CountryViewModelFactory
import com.example.petinfo.ui.theme.PetInfoTheme

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appContainer = AppContainer(this)

        val getCountryUseCase = GetCountryUseCase(
            appContainer.countryRepository
        )

        val getCountriesByRegionUseCase = GetCountriesByRegionUseCase(
            appContainer.countryRepository
        )

        setContent {
            PetInfoTheme {
                CountryScreen(
                    factory = CountryViewModelFactory(
                        getCountryUseCase,
                        getCountriesByRegionUseCase
                    )
                )
            }
        }
    }
}
