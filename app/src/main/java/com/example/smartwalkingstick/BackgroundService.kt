package com.example.smartwalkingstick

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import java.util.*

class BackgroundService : Service(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech


    override fun onCreate() {
        super.onCreate()
        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Use this service to process sensor data
        // Example to speak sensor data
        val sensorData = "Heart Rate is 75 beats per minute"
        speakOut(sensorData)
        return START_STICKY
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.US
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}