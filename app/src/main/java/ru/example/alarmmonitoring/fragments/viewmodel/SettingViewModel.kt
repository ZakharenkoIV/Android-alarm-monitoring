package ru.example.alarmmonitoring.fragments.viewmodel

import android.widget.Button
import android.widget.RadioButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.integration.DataClient
import ru.example.alarmmonitoring.integration.MessageController
import ru.example.alarmmonitoring.integration.PubNubClient

class SettingViewModel : ViewModel() {
    private lateinit var messageController: MessageController
    private var dataClient: DataClient? = null

    var activeEngineer: String = "engineer"
    private val serverPushy = "Pushy"
    private val serverPusher = "Pusher"
    private val serverPubNub = "PubNub"
    private var serverNumber = 0
    private val serverName = listOf(serverPushy, serverPusher, serverPubNub)
    var engineer1RadioButtonIsChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    var engineer2RadioButtonIsChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    var engineer1RadioButtonIsEnabled: MutableLiveData<Boolean> = MutableLiveData(true)
    var engineer2RadioButtonIsEnabled: MutableLiveData<Boolean> = MutableLiveData(true)
    var toggleSwitchIsChecked: MutableLiveData<Boolean> = MutableLiveData(false)
    var toggleSwitchIsEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var switchTransactionTime: MutableLiveData<Long> = MutableLiveData()
    var timeImprint: MutableLiveData<Long> = MutableLiveData()

    @JvmField
    val isSuccessfulConnection: MutableLiveData<Boolean> = MutableLiveData(false)
    private val timeoutMillis: Long = 3500

    private val _button1Background = MutableLiveData("not_work_circle")
    val button1Background: LiveData<String>
        get() = _button1Background

    fun setButton1Background(newBackground: String) {
        _button1Background.postValue(newBackground)
    }

    private val _button2Background = MutableLiveData("not_work_circle")
    val button2Background: LiveData<String>
        get() = _button2Background

    fun setButton2Background(newBackground: String) {
        _button2Background.postValue(newBackground)
    }

    private val _button3Background = MutableLiveData("not_work_circle")
    val button3Background: LiveData<String>
        get() = _button3Background

    fun setButton3Background(newBackground: String) {
        _button3Background.postValue(newBackground)
    }

    private val _employmentPicture1Background = MutableLiveData("unknown")
    val employmentPicture1Background: LiveData<String>
        get() = _employmentPicture1Background

    fun setEmploymentPicture1Background(newBackground: String) {
        _employmentPicture1Background.postValue(newBackground)
    }

    private val _employmentPicture2Background = MutableLiveData("unknown")
    val employmentPicture2Background: LiveData<String>
        get() = _employmentPicture2Background

    fun setEmploymentPicture2Background(newBackground: String) {
        _employmentPicture2Background.postValue(newBackground)
    }

    fun validSwitchTransaction(): Boolean {
        return switchTransactionTime.value == timeImprint.value
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun connectToServer() {
        val limitAttempts = serverName.size * 2
        for (attempt in 1..limitAttempts) {
            val deferredResult = GlobalScope.async {
                if (validSwitchTransaction()) {
                    toConnect()
                    delay(timeoutMillis)
                }
            }
            runBlocking {
                deferredResult.await()
            }
            if (serverNumber == serverName.size - 1) {
                serverNumber = 0
            } else {
                serverNumber += 1
            }
            if (isSuccessfulConnection.value == true) {
                break
            }
        }
    }

    fun determineEmployment() {
        when (messageController.employment) {
            "0" -> {
                setEmploymentPicture2Background("free")
                setEmploymentPicture1Background("free")
            }

            "1" -> {
                setEmploymentPicture2Background("busy")
                setEmploymentPicture1Background("free")
            }

            "2" -> {
                setEmploymentPicture2Background("free")
                setEmploymentPicture1Background("busy")
            }

            "3" -> {
                setEmploymentPicture2Background("busy")
                setEmploymentPicture1Background("busy")
            }
            else -> {
                setEmploymentPicture2Background("unknown")
                setEmploymentPicture1Background("unknown")
            }
        }
    }

    private fun toConnect() {
        var dataClient: DataClient? = null
        if (serverName[serverNumber] == serverPushy) {
            // dataClient = PushyClient(activeEngineer)
        } else if (serverName[serverNumber] == serverPusher) {
            // dataClient = PusherClient(activeEngineer)
        } else if (serverName[serverNumber] == serverPubNub) {
            dataClient = PubNubClient(activeEngineer, messageController)
        }
        val serverStatusBackground: MutableLiveData<String> = when (serverNumber) {
            0 -> _button1Background
            1 -> _button2Background
            2 -> _button3Background
            else -> return
        }
        dataClient?.startPolling(serverStatusBackground, this)
    }

    fun disconnectToServer() {
        dataClient?.stopPolling()
    }

    fun setMessageController(messageController: MessageController) {
        this.messageController = messageController
    }

    companion object {
        @BindingAdapter("android:background")
        @JvmStatic
        fun setBackgroundResource(button: Button, resource: String?) {
            if (resource != null) {
                val drawableResId = getDrawableResId(resource)
                button.setBackgroundResource(drawableResId)
            }
        }

        @BindingAdapter("drawableEndRes")
        @JvmStatic
        fun setDrawableEndRes(view: RadioButton, drawableName: String) {
            val drawableResId = getDrawableResId(drawableName)
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableResId, 0)
        }

        private fun getDrawableResId(state: String?): Int {
            return when (state) {
                "loading_circle" -> R.drawable.loading_circle
                "not_work_circle" -> R.drawable.not_work_circle
                "ready_circle" -> R.drawable.ready_circle
                "ok_circle" -> R.drawable.ok_circle
                "unknown" -> R.drawable.unknown
                "free" -> R.drawable.free
                "busy" -> R.drawable.busy
                "ok" -> R.drawable.ok
                else -> R.drawable.loss_of_contact_circle
            }
        }
    }
}
