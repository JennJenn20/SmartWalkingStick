package com.example.smartwalkingstick.sensors

class GpsNavigation {
    fun getCurrentLocation(): String {
        return listOf("Bangalore", "Delhi", "Mumbai", "Chennai").random()
    }

    fun getDestination(): String {
        return listOf("Home", "Hospital", "Park", "Market").random()
    }

    fun getDistance(): Double {
        return (1..10).random().toDouble()
    }

    fun getDirection(): String {
        return listOf("North", "South", "East", "West").random()
    }

    fun getNavigationStatus(): String {
        return listOf("Normal", "High", "Low").random()
    }

    fun getNavigationTrend(): String {
        return listOf("Increasing", "Decreasing", "Stable").random()
    }

    fun getNavigationActivity(): String {
        return listOf("Resting", "Walking", "Running").random()
    }
}