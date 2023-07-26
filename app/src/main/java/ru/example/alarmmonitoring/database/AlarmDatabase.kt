package ru.example.alarmmonitoring.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.example.alarmmonitoring.database.dao.LogTableDao
import ru.example.alarmmonitoring.database.entity.LogTableItem

@Database(entities = [LogTableItem::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun logTableDao(): LogTableDao
}