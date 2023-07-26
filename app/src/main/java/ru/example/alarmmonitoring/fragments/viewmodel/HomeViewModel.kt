package ru.example.alarmmonitoring.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.example.alarmmonitoring.data.HomeTableLine
import ru.example.alarmmonitoring.integration.MessageController

class HomeViewModel : ViewModel() {
    private lateinit var messageController: MessageController
    val tableData = MutableLiveData<List<HomeTableLine>>(emptyList())

    private val _button1State = MutableLiveData("not_work_circle")
    val button1State: LiveData<String>
        get() = _button1State

    private val _button2State = MutableLiveData("not_work_circle")
    val button2State: LiveData<String>
        get() = _button2State

    private val _button3State = MutableLiveData("not_work_circle")
    val button3State: LiveData<String>
        get() = _button3State

    fun setButton1State(newState: String) {
        _button1State.value = newState
    }

    fun setButton2State(newState: String) {
        _button2State.value = newState
    }

    fun setButton3State(newState: String) {
        _button3State.value = newState
    }

    fun setMessageController(messageController: MessageController) {
        this.messageController = messageController
        messageController.setHomeTableData(tableData)
    }
}