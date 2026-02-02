package com.example.petinfo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(

    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),

    labelMedium = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    )
)
