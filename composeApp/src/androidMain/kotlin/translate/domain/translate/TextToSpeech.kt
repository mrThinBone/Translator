package translate.domain.translate

import android.widget.Toast
import androidx.core.text.isDigitsOnly
import core.domain.language.Language
import core.domain.language.toLocale
import org.example.translator.TranslateApp

actual class TextToSpeech {

    actual fun speak(text: String, language: Language) {
        if (text.isBlank() || text.isDigitsOnly()) return
        val tts: android.speech.tts.TextToSpeech = TranslateApp.tts
        if (tts.isSpeaking) {
            return
        }
        val locale: java.util.Locale? = language.toLocale()
        var message = ""
        if (locale == null) {
            message = "lang is not supported"
        } else {
            if (tts.isLanguageAvailable(locale) == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                message = "lang is not supported"
            }
            tts.language = locale
        }
        if (message.isNotEmpty()) {
            Toast.makeText(TranslateApp.self, message, Toast.LENGTH_SHORT).show()
            return
        }

        val result = tts.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
        if (result == android.speech.tts.TextToSpeech.ERROR) {
            Toast.makeText(TranslateApp.self, "tts error", Toast.LENGTH_SHORT).show()
        }
    }

}