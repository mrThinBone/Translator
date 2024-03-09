import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.darkColors
import core.theme.lightColors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import translator.composeapp.generated.resources.Res
import translator.composeapp.generated.resources.compose_multiplatform
import translator.composeapp.generated.resources.sf_pro_text_bold
import translator.composeapp.generated.resources.sf_pro_text_medium
import translator.composeapp.generated.resources.sf_pro_text_regular

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val sfProText = FontFamily(
        Font(
            resource = Res.font.sf_pro_text_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.sf_pro_text_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.sf_pro_text_bold,
            weight = FontWeight.Bold
        ),
    )
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) {
            darkColors
        } else {
            lightColors
        },
        typography = Typography(
            h1 = TextStyle(
                fontFamily = sfProText,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            h2 = TextStyle(
                fontFamily = sfProText,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            h3 = TextStyle(
                fontFamily = sfProText,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            ),
            body1 = TextStyle(
                fontFamily = sfProText,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            body2 = TextStyle(
                fontFamily = sfProText,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
        ),
        shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(4.dp),
            large = RoundedCornerShape(0.dp)
        )
    ) {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}