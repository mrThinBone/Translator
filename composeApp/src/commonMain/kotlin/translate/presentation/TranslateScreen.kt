@file:OptIn(ExperimentalResourceApi::class)

package translate.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import translate.domain.translate.TranslateError
import translate.presentation.components.LanguageDropDown
import translate.presentation.components.SwapLanguagesButton
import translate.presentation.components.TranslateHistoryItem
import translate.presentation.components.TranslateTextField
import translator.composeapp.generated.resources.Res
import translator.composeapp.generated.resources.mic

@Composable
fun TranslateScreen(
    state: State<TranslateState>,
    onEvent: (TranslateEvent) -> Unit
) {
    val context = LocalContext.current

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
                    onEvent(TranslateEvent.RecordAudio)
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