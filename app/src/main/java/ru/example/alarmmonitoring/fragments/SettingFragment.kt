package ru.example.alarmmonitoring.fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.example.alarmmonitoring.MainActivity
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.databinding.FragmentSettingBinding
import ru.example.alarmmonitoring.fragments.viewmodel.SettingViewModel

class SettingFragment : Fragment() {
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var binding: FragmentSettingBinding
    private var connectionJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel = (requireActivity() as MainActivity).getSettingViewModel()
        binding = DataBindingUtil.bind(view)!!
        binding.viewModel = settingViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        when (settingViewModel.activeEngineer) {
            "engineer1" -> {
                settingViewModel.activeEngineer = "engineer1"
                settingViewModel.engineer1RadioButtonIsChecked.value = true
            }

            "engineer2" -> {
                settingViewModel.activeEngineer = "engineer2"
                settingViewModel.engineer2RadioButtonIsChecked.value = true
            }
        }

        binding.toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onToggleSwitchEnabled()
            } else {
                onToggleSwitchDisabled()
            }
        }

        binding.engineerRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.engineer1_radio_button -> onEngineerRadioButtonChecked("engineer1")
                R.id.engineer2_radio_button -> onEngineerRadioButtonChecked("engineer2")
            }
        }
    }

    private fun onToggleSwitchEnabled() {
        if (settingViewModel.toggleSwitchIsChecked.value == false) {
            settingViewModel.switchTransactionTime.value = SystemClock.elapsedRealtime()
            settingViewModel.timeImprint.value = settingViewModel.switchTransactionTime.value
            settingViewModel.toggleSwitchIsChecked.postValue(true)
            settingViewModel.setButton1Background("ready_circle")
            settingViewModel.setButton2Background("ready_circle")
            settingViewModel.setButton3Background("ready_circle")
            settingViewModel.setEmploymentPicture1Background("unknown")
            settingViewModel.setEmploymentPicture2Background("unknown")
            settingViewModel.engineer1RadioButtonIsEnabled.postValue(false)
            settingViewModel.engineer2RadioButtonIsEnabled.postValue(false)
            connectionJob = CoroutineScope(Dispatchers.IO).launch {
                if (settingViewModel.validSwitchTransaction()) {
                    settingViewModel.connectToServer()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onToggleSwitchDisabled() {
        settingViewModel.switchTransactionTime.postValue(SystemClock.elapsedRealtime())
        settingViewModel.toggleSwitchIsChecked.postValue(false)
        settingViewModel.toggleSwitchIsEnabled.postValue(false)
        settingViewModel.setButton1Background("not_work_circle")
        settingViewModel.setButton2Background("not_work_circle")
        settingViewModel.setButton3Background("not_work_circle")
        settingViewModel.engineer1RadioButtonIsEnabled.postValue(true)
        settingViewModel.engineer2RadioButtonIsEnabled.postValue(true)
        connectionJob?.cancel()
        settingViewModel.disconnectToServer()
        CoroutineScope(Dispatchers.IO).launch {
            val deferredResult = GlobalScope.async {
                delay(2000)
            }
            runBlocking {
                deferredResult.await()
            }
            settingViewModel.toggleSwitchIsEnabled.postValue(true)
        }
    }

    private fun onEngineerRadioButtonChecked(activeEngineer: String) {
        settingViewModel.toggleSwitchIsEnabled.postValue(true)
        settingViewModel.activeEngineer = activeEngineer
    }
}