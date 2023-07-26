package ru.example.alarmmonitoring.fragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.example.alarmmonitoring.data.LogTableLine
import ru.example.alarmmonitoring.integration.MessageController

class LogViewModel : ViewModel() {
    private lateinit var messageController: MessageController
    val tableData = MutableLiveData<List<LogTableLine>>(emptyList())

    fun setMessageController(messageController: MessageController) {
        this.messageController = messageController
        messageController.setLogTableData(tableData)
    }
}