package com.example.smartwalkingstick

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class HealthMonitorActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechIntent: Intent
    private lateinit var textViewCommand: TextView
    private lateinit var btnVoiceCommand: Button
    private lateinit var textViewHealthData: TextView

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_monitor)

        // Initialize UI components
        textViewCommand = findViewById(R.id.textViewCommand)
        btnVoiceCommand = findViewById(R.id.btnVoiceCommand)
        textViewHealthData = findViewById(R.id.textViewHealthData)

        // Check for microphone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }

        // Initialize SpeechRecognizer and Intent
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

        // Set listener for the Voice Command button
        btnVoiceCommand.setOnClickListener {
            startListening()
        }

        // Initialize SpeechRecognizer listener
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext, "Listening for command...", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                Toast.makeText(applicationContext, "Stopped Listening", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Int) {
                Toast.makeText(applicationContext, "Error: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                // Get the speech recognition result
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    val command = matches[0]
                    textViewCommand.text = "Recognized Command: $command"
                    handleVoiceCommand(command)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun startListening() {
        speechRecognizer.startListening(speechIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Microphone permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle different voice commands
    private fun handleVoiceCommand(command: String) {
        when {
            command.contains("heart rate", ignoreCase = true) -> {
                // Fetch and display heart rate data (placeholder)
                textViewHealthData.text = "Current Heart Rate: 72 BPM"
            }
            command.contains("steps", ignoreCase = true) -> {
                // Fetch and display steps data (placeholder)
                textViewHealthData.text = "Today's Steps: 4,500"
            }
            else -> {
                textViewHealthData.text = "Unknown command. Please try again."
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy() // Clean up resources
    }
}
