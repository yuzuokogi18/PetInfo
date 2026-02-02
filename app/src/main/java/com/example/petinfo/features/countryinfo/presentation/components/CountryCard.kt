package com.example.petinfo.features.countryinfo.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petinfo.features.countryinfo.domain.entites.Country

@Composable
fun CountryCard(
    country: Country,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp)
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
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (country.isFavorite)
                        "Quitar de favoritos"
                    else
                        "Agregar a favoritos"
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(
            modifier = Modifier.padding(top = 6.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
