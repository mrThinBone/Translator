package translate.domain.translate

import core.domain.language.Language

expect class TextToSpeech {

    fun speak(text: String, language: Language)

}