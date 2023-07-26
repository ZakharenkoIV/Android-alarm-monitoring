package ru.example.alarmmonitoring.integration;

import androidx.lifecycle.MutableLiveData;

import ru.example.alarmmonitoring.fragments.viewmodel.SettingViewModel;

public interface DataClient {

    void startPolling(MutableLiveData<String> buttonBackground, SettingViewModel settingViewModel);

    void stopPolling();

}
