package com.example.smartwalkingstick

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Find the ImageView and start the fade-in animation
        val logo = findViewById<ImageView>(R.id.splash_logo)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeIn)

        // Delay for the splash screen (e.g., 3 seconds), then move to the next activity
        Handler().postDelayed({

            // Start the SignUp Activity or the next screen after the splash screen
            val intent = Intent(this@SplashScreenActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish() // Close the SplashScreenActivity so it won't show on back button press
        }, 3000) // 3000 ms = 3 seconds delay


    }
}