package voicetotext.domain

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import core.domain.util.CommonStateFlow
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.example.translator.TranslateApp

actual class VoiceToTextParser : RecognitionListener {

    private val recognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(TranslateApp.self)
    }

    private val _state = MutableStateFlow(VoiceToTextParserState())
    actual val state: CommonStateFlow<VoiceToTextParserState> =
        _state.toCommonStateFlow()

    actual fun startListening(languageCode: String) {
        _state.update { VoiceToTextParserState() }

        if(!SpeechRecognizer.isRecognitionAvailable(TranslateApp.self)) {
            _state.update { it.copy(
                error = "Speech recognizer is not available"
            ) }
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }
        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
        _state.update { it.copy(
            isSpeaking = true
        ) }
    }

    actual fun stopListening() {
        _state.update { VoiceToTextParserState() }
        recognizer.stopListening()
    }

    actual fun cancel() {
        recognizer.cancel()
    }

    actual fun reset() {
        _state.value = VoiceToTextParserState()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update { it.copy(error = null) }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsDb: Float) {
        /**
         * 12f - 2f is the maximum and minimum value of rms
         */
        _state.update { it.copy(
            powerRatio = rmsDb * (1f / (12f - (-2f)))
        ) }
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update { it.copy(isSpeaking = false) }
    }

    override fun onError(code: Int) {
        _state.update { it.copy(error = "Error: $code") }
    }

    override fun onResults(result: Bundle?) {
        result
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { text ->
                _state.update { it.copy(
                    result = text
                ) }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit

}