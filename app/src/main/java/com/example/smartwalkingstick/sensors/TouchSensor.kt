package com.example.smartwalkingstick.sensors

class TouchSensor {
    fun isTouched(): Boolean {
        return (0..1).random() == 1
    }

}