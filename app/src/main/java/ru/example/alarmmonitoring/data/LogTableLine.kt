package ru.example.alarmmonitoring.data

data class LogTableLine(
    val time: String,
    val sensor: String,
    val sensorDescription: String,
    val actualValue: String,
    val boundaryValue: String,
    val alarmMessage: String,
    val signalClass: String
)