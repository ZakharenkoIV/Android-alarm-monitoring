package ru.example.alarmmonitoring.database

import android.app.Application
import androidx.room.Room
import ru.example.alarmmonitoring.database.dao.LogTableDao

class MyDatabaseProvider(application: Application) {
    private val database: AlarmDatabase = Room.databaseBuilder(
        application,
        AlarmDatabase::class.java, "alarm_database"
    ).build()

    fun provideMyDataDao(): LogTableDao {
        return database.logTableDao()
    }
}