package com.example.smartwalkingstick.sensors

class ObstacleDetectionSensor {
    fun isObstacleDetected(): Boolean {
        return (0..1).random() == 1
    }

fun getObstacleDistance(): Int {
        return (0..100).random()
    }

    fun getObstacleDirection(): String {
        return listOf("Front", "Back", "Left", "Right").random()
    }

    fun getObstacleType(): String {
        return listOf("Human", "Animal", "Object").random()
    }

    fun getObstacleSize(): String {
        return listOf("Small", "Medium", "Large").random()
    }

    fun getObstacleColor(): String {
        return listOf("Red", "Green", "Blue", "Yellow", "Black", "White").random()
    }

    fun getObstacleSpeed(): String {
        return listOf("Slow", "Medium", "Fast").random()
    }

}