package ru.example.alarmmonitoring.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_table")
data class LogTableItem(
    @PrimaryKey
    val sensorCode: String,
    val sensorDescription: String,
    val minValue: String,
    val maxValue: String,
    val alarmMinMessage: String,
    val alarmMaxMessage: String
)
