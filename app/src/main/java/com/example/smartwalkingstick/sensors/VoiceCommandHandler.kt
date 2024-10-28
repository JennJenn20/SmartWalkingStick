package com.example.smartwalkingstick.sensors

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class VoiceCommandHandler {
    private var language = "en-US"

    fun setLanguage(language: String) {
        this.language = language
    }
    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language)
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language)
        (context as AppCompatActivity).startActivityForResult(intent, REQUEST_VOICE_COMMAND)
    }

    fun processVoiceCommand(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_VOICE_COMMAND && resultCode == AppCompatActivity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0)
            // Process the spoken text here
        }
    }

    companion object {
        const val REQUEST_VOICE_COMMAND = 100
    }
}