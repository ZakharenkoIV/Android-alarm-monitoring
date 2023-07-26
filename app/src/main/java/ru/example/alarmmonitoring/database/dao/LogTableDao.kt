package ru.example.alarmmonitoring.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.example.alarmmonitoring.database.entity.LogTableItem

@Dao
interface LogTableDao {
    @Insert
    fun insert(data: LogTableItem)

    @Query("SELECT * FROM log_table")
    fun getAllData(): List<LogTableItem>

    @Query("SELECT * FROM log_table WHERE sensorCode = :signalCode")
    fun getItem(signalCode: String): LogTableItem
}