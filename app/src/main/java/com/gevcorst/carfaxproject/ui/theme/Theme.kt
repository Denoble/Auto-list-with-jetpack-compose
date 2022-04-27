package values

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.codelab.theming.ui.start.theme.*


@Composable
fun CarFaxTheme(darkTheme:Boolean = isSystemInDarkTheme(),
                 content: @Composable () -> Unit) {
    MaterialTheme(  colors = if (darkTheme) DarkColors else LightColors,
        typography = CarfaxTypography,
        shapes = CarfaxShapes,
        content = content)
}
val LightColors = lightColors(
    primary = Purple500,
    primaryVariant = Purple200,
    secondary = Red700,
    secondaryVariant = Red700,
    error = Red800,
)

private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)

