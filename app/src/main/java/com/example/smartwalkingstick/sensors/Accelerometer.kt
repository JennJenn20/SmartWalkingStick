package com.example.smartwalkingstick.sensors

class Accelerometer {
    fun getX(): Double {
        return (1..10).random().toDouble()
    }

    fun getY(): Double {
        return (1..10).random().toDouble()
    }

    fun getZ(): Double {
        return (1..10).random().toDouble()
    }

    fun getAccelerometerStatus(): String {
        return listOf("Normal", "High", "Low").random()
    }
}