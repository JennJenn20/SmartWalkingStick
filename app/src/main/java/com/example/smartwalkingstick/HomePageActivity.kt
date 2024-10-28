package com.example.smartwalkingstick

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import com.example.smartwalkingstick.EmergencyContactsDialogFragment

class HomePageActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var temperatureTextView: TextView
    private lateinit var spO2TextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize views
        mapView = findViewById(R.id.mapView2)
        temperatureTextView = findViewById(R.id.temperatureTextView) // Ensure you have this ID in your XML
        spO2TextView = findViewById(R.id.sp02TextView)
        val heartRateTextView = findViewById<TextView>(R.id.heartRateTextView)

        // Initialize MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Fetch data from Intent
        val heartRate = intent.getIntExtra("heartRate", 0)
        val spO2 = intent.getIntExtra("spO2", 0)
        val temperature = intent.getFloatExtra("temperature", 0.0f)

        // Display data
        heartRateTextView.text = "Heart Rate: $heartRate bpm"
        spO2TextView.text = "SpO2: $spO2%"
        temperatureTextView.text = "Temperature: $temperature°C"

        // Fetch weather data
        fetchWeatherData()
    }

    val showEmergencyContactsButton: Button = findViewById(R.id.showEmergencyContactsButton)
    showEmergencyContactsButton.setOnClickListener {
        // Show the Emergency Contacts Dialog
        val dialog = EmergencyContactsDialogFragment()
        dialog.show(supportFragmentManager, "EmergencyContactsDialogFragment")
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set a marker at a location
        val location = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    private fun fetchWeatherData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=YourCity&appid=YourAPIKey&units=metric")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.use { responseBody ->
                    val responseString = responseBody.string()
                    val jsonObject = JSONObject(responseString)
                    val main = jsonObject.getJSONObject("main")
                    val temperature = main.getDouble("temp")

                    // Update the UI on the main thread
                    runOnUiThread {
                        temperatureTextView.text = "Temperature: $temperature °C"
                    }
                }
            }
        })
    }
}
