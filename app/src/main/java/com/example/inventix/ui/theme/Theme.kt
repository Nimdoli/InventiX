package com.example.inventix.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = MaroonPrimary,
    onPrimary = Color.White,
    secondary = AccentYellow,
    onSecondary = ButtonTextAmber,
    background = Color.White,
    onBackground = DarkValue,
    surface = Color.White,
    onSurface = DarkValue,
    surfaceVariant = CreamSurface,
    onSurfaceVariant = MutedText,
    outline = BorderBeige,
    error = DangerRed
)

@Composable
fun InventixTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = InventixTypography,
        content = content
    )
}
