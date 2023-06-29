package ru.example.alarmmonitoring.data

data class TableItem(
    val time: String,
    val sensor: String,
    val sensorDescription: String,
    val actualValue: String,
    val expectedValue: String,
    val alarmMessage: String,
    val recommendationMessage: String
)