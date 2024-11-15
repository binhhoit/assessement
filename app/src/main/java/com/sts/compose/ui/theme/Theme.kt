package com.sts.compose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = GreenApp,
    secondary = BgDarkBottomBar, // Bg Bottom bar
    tertiary = GreenApp,

    background = BgDarkApp,
    surface = BgDarkApp,
    onPrimary = Color.White, // text
    onSecondary = Orange, // Button
    onTertiary = Gray7C, // Text Grey
    onBackground = Color(0xFF1C1B1F),
    onSurface = Gray9E,
    outline = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GreenApp,
    secondary = BgLightBottomBar,
    tertiary = GreenApp,

    background = BgLightApp,
    surface = BgLightApp,
    onPrimary = Color.Black,
    onSecondary = Orange,
    onTertiary = Gray7C,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Gray9E,
    outline = Gray9E
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor =
                colorScheme.primary.toArgb()
            @Suppress("DEPRECATION")
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars =
                darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
