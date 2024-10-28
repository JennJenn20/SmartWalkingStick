package com.example.smartwalkingstick.sensors

class FallDetectionSensor {
    fun isFallDetected(): Boolean {
        return (0..1).random() == 1
    }
}