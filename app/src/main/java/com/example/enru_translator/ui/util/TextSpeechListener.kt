package com.example.enru_translator.ui.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class TextSpeechListener(context: Context) : TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locale = Locale("ru")
            tts.language = locale
            tts.setPitch(1.3f)
            tts.setSpeechRate(0.9f)
            tts.isLanguageAvailable(locale)
        }
    }

    fun speakFromText(text: CharSequence) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

}