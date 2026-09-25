package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AnimeDarkColorScheme =
  darkColorScheme(
    primary = AnimeGold,
    onPrimary = Color(0xFF1A1200),
    primaryContainer = Color(0xFF4A3700),
    onPrimaryContainer = AnimeGoldLight,
    secondary = AnimeCyan,
    onSecondary = Color(0xFF002026),
    secondaryContainer = Color(0xFF004D59),
    onSecondaryContainer = Color(0xFF80F2FF),
    tertiary = AnimeGreen,
    onTertiary = Color(0xFF00220E),
    background = AnimeNavyDark,
    onBackground = Color(0xFFECEFF8),
    surface = AnimeNavyMedium,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = AnimeNavyCard,
    onSurfaceVariant = Color(0xFFCBD5E1),
    error = AnimeRed,
    onError = Color.White
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force the rich anime gaming dark aesthetic
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = AnimeDarkColorScheme
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = AnimeNavyDark.toArgb()
      window.navigationBarColor = AnimeNavyDark.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
      WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
    }
  }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
