package com.example.smartwalkingstick.sensors

class Sp02Sensor {
    fun getSp02(): Int {
        return (90..100).random()
    }

    fun getSp02Status(): String {
        return listOf("Normal", "High", "Low").random()
    }
}