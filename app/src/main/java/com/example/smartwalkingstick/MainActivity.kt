package com.example.smartwalkingstick

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.smartwalkingstick.sensors.*

private lateinit var bluetoothAdapter: BluetoothAdapter
private const val REQUEST_ENABLE_BT = 1
private const val REQUEST_VOICE_COMMAND = 2
private const val CHANNEL_ID = "BluetoothChannel"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Bluetooth Adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Check if Bluetooth is supported on the device
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if Bluetooth is enabled, request to enable if not
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        // Check and request Bluetooth permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_ENABLE_BT)
        }

        // Create notification channel
        createNotificationChannel()

        // Assuming the sensor triggers this method when touched
        sensorTouchDetected()
    }

    private fun sensorTouchDetected() {
        if (isFirstConnection()) {
            // Prompt the user via voice and notification
            promptUserForPairing()
        } else {
            // Directly connect without prompting
            connectToStick()
        }
    }

    private fun isFirstConnection(): Boolean {
        // Check if this is the first connection
        val sharedPref = getSharedPreferences("BluetoothPrefs", MODE_PRIVATE)
        return sharedPref.getBoolean("isFirstConnection", true)
    }

    private fun connectToStick() {
        // Implement the connection logic with the stick
        // After connecting successfully, store the status
        val sharedPref = getSharedPreferences("BluetoothPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isFirstConnection", false)
        editor.apply()

        Toast.makeText(this, "Connected to the stick", Toast.LENGTH_SHORT).show()

        // Navigate to HomePageActivity
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()

    }


    private fun promptUserForPairing() {
        // Ask the user via voice command if they want to pair
        askForVoiceCommand()

        // Send a notification to sighted users with options
        sendPairingNotification()
    }

    private fun askForVoiceCommand() {
        // Use Android Speech API to ask and receive a response
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Do you want to pair the stick? Say 'yes' or 'no'.")
        startActivityForResult(intent, REQUEST_VOICE_COMMAND)
    }

    private fun sendPairingNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, "BluetoothChannel")
            .setContentTitle("Bluetooth Pairing")
            .setContentText("Do you want to pair with the stick?")
            .setSmallIcon(R.drawable.baseline_bluetooth_24)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_notifications_24, "Yes", getPairingIntent(true))
            .addAction(R.drawable.baseline_notifications_off_24, "No", getPairingIntent(false))
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun getPairingIntent(shouldPair: Boolean): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("shouldPair", shouldPair)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_VOICE_COMMAND && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val command = results?.get(0)?.toLowerCase()

            if (command == "yes") {
                connectToStick()
            } else {
                Toast.makeText(this, "Stick not paired", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Bluetooth Channel"
            val descriptionText = "Channel for Bluetooth pairing notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}