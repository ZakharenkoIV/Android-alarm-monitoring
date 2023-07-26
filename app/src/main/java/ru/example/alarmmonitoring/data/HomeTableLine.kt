package ru.example.alarmmonitoring.data

data class HomeTableLine(
    val time: String,
    val sensor: String,
    val value: String,
    val alarmMessage: String
)