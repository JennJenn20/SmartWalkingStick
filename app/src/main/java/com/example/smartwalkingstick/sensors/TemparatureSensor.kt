package com.example.smartwalkingstick.sensors

class TemparatureSensor {
    fun getTemparature(): Int {
        return (36..38).random()
    }

    fun getTemparatureStatus(): String {
        return listOf("Normal", "High", "Low").random()
    }
}