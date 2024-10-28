package com.example.smartwalkingstick.sensors

class HeartRateSensor {
    fun getHeartRate(): Int {
        return (60..100).random()
    }

    fun getHeartRateStatus(): String {
        return listOf("Normal", "High", "Low").random()
    }

    fun getHeartRateRange(): String {
        return listOf("60-100", "100-150", "150-200").random()
    }

    fun getHeartRateTrend(): String {
        return listOf("Increasing", "Decreasing", "Stable").random()
    }

    fun getHeartRateActivity(): String {
        return listOf("Resting", "Walking", "Running").random()
    }

    fun getHeartRateIntensity(): String {
        return listOf("Low", "Medium", "High").random()
    }
}