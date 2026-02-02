package com.example.petinfo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryBlue,
    tertiary = AccentCyan,

    background = BackgroundLight,
    surface = SurfaceLight,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,

    error = FavoriteRed
)

private val DarkColorScheme = darkColorScheme(
    primary = SecondaryBlue,
    secondary = AccentCyan,
    tertiary = PrimaryBlue,

    background = BackgroundDark,
    surface = SurfaceDark,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,

    error = FavoriteRed
)

@Composable
fun PetInfoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
