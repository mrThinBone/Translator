import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.darkColors
import core.theme.lightColors
import di.commonModule
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import translate.di.translateModule
import translate.domain.history.HistoryDataSource
import translate.domain.translate.TranslateUseCase
import translate.presentation.TranslateScreen
import translate.presentation.TranslateViewModel
import translate.presentation.provideTranslateViewModel
import translator.composeapp.generated.resources.Res
import translator.composeapp.generated.resources.sf_pro_text_bold
import translator.composeapp.generated.resources.sf_pro_text_medium
import translator.composeapp.generated.resources.sf_pro_text_regular

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(
            listOf(
                commonModule,
                translateModule
            )
        )
    }) {
        MainContent()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun MainContent() {
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
        val useCase = koinInject<TranslateUseCase>()
        val historyDataSource = koinInject<HistoryDataSource>()
        val viewModel: TranslateViewModel = provideTranslateViewModel(useCase, historyDataSource)

        TranslateScreen(viewModel.state.collectAsState()) { event -> viewModel.onEvent(event) }
    }
}