package ru.example.alarmmonitoring.integration;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import ru.example.alarmmonitoring.data.HomeTableLine;
import ru.example.alarmmonitoring.data.LogTableLine;
import ru.example.alarmmonitoring.database.dao.LogTableDao;
import ru.example.alarmmonitoring.database.entity.LogTableItem;

public class MessageController {
    private final static String SEPARATOR = " ";
    private final LogTableDao logTableDao;
    private String employment;

    private String activeEngineer;

    private MutableLiveData<List<LogTableLine>> logTableData = new MutableLiveData<>();
    // private MutableLiveData<List<HomeTableLine>> homeTableData = new MutableLiveData<>();

    private MutableLiveData<List<HomeTableLine>> homeTableData;

    public MessageController(@NotNull LogTableDao logTableDao) {
        this.logTableDao = logTableDao;
    }

    public String getEmployment() {
        return employment;
    }

    public void setActiveEngineer(String activeEngineer) {
        this.activeEngineer = activeEngineer;
    }

    // пример msg - "1 1 1 E121 10:05 70"
    public void toAccept(String msg) {
        String[] s = msg.split(SEPARATOR);
        employment = s[0]; //  занятость инженеров(0 -свободны оба, 1 - занят первый, 2 - занят второй, 3 - заняты оба),
        String engineerNumber = s[1]; //  номер инженера, (1 - сообщение для первого, 2 - сообщение для второго, 3 сообщение для обоих)
        String signalClass = s[2]; //  класс сигнала (0 - инфо, 1 - жёлтый, 3 - красный),
        String signalCode = s[3]; //  код сигнала (это номер датчика),
        String signalTime = s[4]; //  время сигнала,
        String actualSignalValue = s[5]; //  актуальное значение сигнала,

        LogTableLine logTableLine = generateLogLine(signalClass, signalCode, signalTime, actualSignalValue);
        addLogLineInLogTable(logTableLine);
        releaseHomeLine(generateHomeTableLine(logTableLine), engineerNumber);
        startSoundAlarm(engineerNumber);
    }

    private void startSoundAlarm(String engineerNumber) {

    }

    private void releaseHomeLine(HomeTableLine homeTableLine, String engineerNumber) {
        if (activeEngineer.equals("engineer1")) {
            if ("1".equals(engineerNumber) || "3".equals(engineerNumber)) {
                addHomeLineInHomeTable(homeTableLine);
            }
        } else if (activeEngineer.equals("engineer2")) {
            if ("2".equals(engineerNumber) || "3".equals(engineerNumber)) {
                addHomeLineInHomeTable(homeTableLine);
            }
        }
    }

    private void addHomeLineInHomeTable(HomeTableLine homeTableLine) {
        List<HomeTableLine> currentTableData = homeTableData.getValue();
        if (currentTableData != null) {
            List<HomeTableLine> updatedTableData = new LinkedList<>(currentTableData);
            updatedTableData.add(0, homeTableLine);
            homeTableData.postValue(updatedTableData);
        }
    }

    private void addLogLineInLogTable(LogTableLine logTableLine) {
        List<LogTableLine> currentTableData = logTableData.getValue();
        if (currentTableData != null) {
            List<LogTableLine> updatedTableData = new LinkedList<>(currentTableData);
            updatedTableData.add(0, logTableLine);
            logTableData.postValue(updatedTableData);
        }
    }

    public LogTableLine generateLogLine(String signalClass, String signalCode,
                                        String signalTime, String actualSignalValue) {
        // LogTableItem tableItem = logTableDao.getItem(signalCode);
        LogTableItem tableItem = new LogTableItem("E121",
                "Какой то датчик",
                "50",
                "100",
                "вот блин, упало",
                "поднялось опасно");
        String sensorDescription = tableItem.getSensorDescription();
        String minValue = tableItem.getMinValue();
        String maxValue = tableItem.getMaxValue();

        String alarmMessage = "";
        if ("0".equals(signalClass)) {
            alarmMessage = "Инфо. ";
        }

//        BackgroundType backgroundType;
//        if ("1".equals(signalClass)) {
//            backgroundType = BackgroundType.YELLOW;
//        } else if ("2".equals(signalClass)) {
//            backgroundType = BackgroundType.RED;
//        } else {
//            backgroundType = BackgroundType.NONE;
//        }

        String boundaryValue;
        int actualValue = Integer.parseInt(actualSignalValue);
        if (actualValue <= Integer.parseInt(minValue)) {
            boundaryValue = " > " + minValue;
            alarmMessage += tableItem.getAlarmMinMessage();
        } else if (actualValue >= Integer.parseInt(maxValue)) {
            boundaryValue = " < " + maxValue;
            alarmMessage += tableItem.getAlarmMaxMessage();
        } else {
            boundaryValue = minValue + " > x < " + maxValue;
        }

        return new LogTableLine(signalTime,
                signalCode,
                sensorDescription,
                actualSignalValue,
                boundaryValue,
                alarmMessage);
    }

    public HomeTableLine generateHomeTableLine(LogTableLine logTableLine) {
        String signalTime = logTableLine.getTime();
        String sensor = logTableLine.getSensor();
        String value = logTableLine.getActualValue() + " (" + logTableLine.getBoundaryValue() + ")";
        String alarmMessage = logTableLine.getAlarmMessage();
        return new HomeTableLine(signalTime, sensor, value, alarmMessage);
    }

    public void setLogTableData(@NotNull MutableLiveData<List<LogTableLine>> logTableData) {
        this.logTableData = logTableData;
    }

    public void setHomeTableData(@NotNull MutableLiveData<List<HomeTableLine>> homeTableData) {
        this.homeTableData = homeTableData;
    }
}
