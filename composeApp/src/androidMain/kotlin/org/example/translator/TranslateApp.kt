package org.example.translator

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech

class TranslateApp : Application() {

    companion object {
        lateinit var self: Context
        lateinit var tts: TextToSpeech
    }

    override fun onCreate() {
        super.onCreate()
        self = this
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {})
    }

}