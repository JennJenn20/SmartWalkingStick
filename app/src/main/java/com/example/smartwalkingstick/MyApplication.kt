package com.example.smartwalkingstick

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TTSManager.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        TTSManager.shutdown()
    }
}