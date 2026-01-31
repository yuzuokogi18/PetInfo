package com.example.petinfo.features.countryinfo.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petinfo.features.countryinfo.domain.entites.Country
import com.example.petinfo.ui.theme.*

@Composable
fun CountryCard(
    country: Country,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = country.flagUrl,
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow("País", country.name)
            InfoRow("Capital", country.capital)
            InfoRow("Región", country.region)
            InfoRow("Población", country.populationReadable)
            InfoRow("Idioma", country.language)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onFavorite,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (country.isFavorite)
                        FavoriteRed else BlueSecondary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (country.isFavorite)
                        "Quitar de favoritos"
                    else
                        "Agregar a favoritos",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary
        )
        Divider(modifier = Modifier.padding(top = 6.dp))
    }
}
