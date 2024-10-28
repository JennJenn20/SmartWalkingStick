package com.example.smartwalkingstick

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

object TTSManager : TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    fun initialize(context: Context) {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            } else {
                isInitialized = true
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Log.e("TTS", "TTS not initialized")
        }
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        isInitialized = false
    }
}