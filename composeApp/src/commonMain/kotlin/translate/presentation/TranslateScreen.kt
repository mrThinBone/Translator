@file:OptIn(ExperimentalResourceApi::class)

package translate.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.koin.java.KoinJavaComponent.inject
import translate.domain.history.HistoryDataSource
import translate.domain.translate.TextToSpeech
import translate.domain.translate.TranslateError
import translate.domain.translate.TranslateUseCase
import translate.presentation.components.LanguageDropDown
import translate.presentation.components.SwapLanguagesButton
import translate.presentation.components.TranslateHistoryItem
import translate.presentation.components.TranslateTextField
import translator.composeapp.generated.resources.Res
import translator.composeapp.generated.resources.mic

class TranslateScreen : Screen {

    @delegate:Transient
    val useCase: TranslateUseCase by inject(TranslateUseCase::class.java)
    @delegate:Transient
    val historyDataSource: HistoryDataSource by inject(HistoryDataSource::class.java)

    @Composable
    override fun Content() {
        val viewModel: TranslateViewModel = provideTranslateViewModel(useCase, historyDataSource)
        TranslateScreenContent(viewModel.state.collectAsState()) { event -> viewModel.onEvent(event) }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TranslateScreenContent(
    state: State<TranslateState>,
    onEvent: (TranslateEvent) -> Unit
) {
    val navigator: Navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val tts = TextToSpeech()

    val onAudioRecorded: (String) -> Unit = { recorded ->
        Log.d("vinhtv", "onAudioRecorded: $recorded")
    }

    LaunchedEffect(key1 = state.value.error) {
        val message = when(state.value.error) {
            TranslateError.SERVICE_UNAVAILABLE -> "error_service_unavailable"
            TranslateError.CLIENT_ERROR -> "client_error"
            TranslateError.SERVER_ERROR -> "server_error"
            TranslateError.UNKNOWN_ERROR -> "unknown_error"
            else -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.push(RecordAudioScreen(onAudioRecorded))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(75.dp)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.mic),
                    contentDescription = "record audio"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguageDropDown(
                        modifier = Modifier.weight(1f),
                        language = state.value.fromLanguage,
                        isOpen = state.value.isChoosingFromLanguage,
                        onClick = {
                            onEvent(TranslateEvent.OpenFromLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguage = {
                            onEvent(TranslateEvent.ChooseFromLanguage(it))
                        }
                    )
                    SwapLanguagesButton(onClick = {
                        onEvent(TranslateEvent.SwapLanguages)
                    })
                    LanguageDropDown(
                        modifier = Modifier.weight(1f),
                        language = state.value.toLanguage,
                        isOpen = state.value.isChoosingToLanguage,
                        onClick = {
                            onEvent(TranslateEvent.OpenToLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguage = {
                            onEvent(TranslateEvent.ChooseToLanguage(it))
                        }
                    )
                }
            } // first item block
            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                TranslateTextField(
                    fromText = state.value.fromText,
                    toText = state.value.toText,
                    isTranslating = state.value.isTranslating,
                    fromLanguage = state.value.fromLanguage,
                    toLanguage = state.value.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = {
                        onEvent(TranslateEvent.ChangeTranslationText(it))
                    },
                    onCopyClick = { text ->
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(text)
                            }
                        )
                        // TODO: string as resource?
                        Toast.makeText(
                            context,
                            "Text copied to clipboard",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onCloseClick = {
                        onEvent(TranslateEvent.CloseTranslation)
                    },
                    onSpeakerClick = {
                        tts.speak(
                            state.value.toText ?: "",
                            state.value.toLanguage.language
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslateEvent.EditTranslation)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } // second item block
            item {
                if (state.value.history.isNotEmpty()) {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.h2
                    )
                }
            }

            items(state.value.history) { item ->
                TranslateHistoryItem(
                    item = item,
                    onClick = {
                        onEvent(TranslateEvent.SelectHistoryItem(item))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}