@file:OptIn(androidx.compose.ui.text.ExperimentalTextApi::class)

package com.example.inventix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.example.inventix.R

val Inter = FontFamily(
    Font(R.font.inter_variable, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.inter_variable, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.inter_variable, weight = FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(R.font.inter_variable, weight = FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
    Font(R.font.inter_variable, weight = FontWeight.Black, variationSettings = FontVariation.Settings(FontVariation.weight(900)))
)

private val Base = Typography()

val InventixTypography = Typography(
    displayLarge = Base.displayLarge.copy(fontFamily = Inter),
    displayMedium = Base.displayMedium.copy(fontFamily = Inter),
    displaySmall = Base.displaySmall.copy(fontFamily = Inter),
    headlineLarge = Base.headlineLarge.copy(fontFamily = Inter),
    headlineMedium = Base.headlineMedium.copy(fontFamily = Inter),
    headlineSmall = Base.headlineSmall.copy(fontFamily = Inter),
    titleLarge = Base.titleLarge.copy(fontFamily = Inter),
    titleMedium = Base.titleMedium.copy(fontFamily = Inter),
    titleSmall = Base.titleSmall.copy(fontFamily = Inter),
    bodyLarge = Base.bodyLarge.copy(fontFamily = Inter),
    bodyMedium = Base.bodyMedium.copy(fontFamily = Inter),
    bodySmall = Base.bodySmall.copy(fontFamily = Inter),
    labelLarge = Base.labelLarge.copy(fontFamily = Inter),
    labelMedium = Base.labelMedium.copy(fontFamily = Inter),
    labelSmall = Base.labelSmall.copy(fontFamily = Inter)
)
