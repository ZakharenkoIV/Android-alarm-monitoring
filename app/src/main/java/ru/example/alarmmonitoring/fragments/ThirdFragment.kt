package ru.example.alarmmonitoring.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.data.TableItem

class ThirdFragment : Fragment() {

    private lateinit var tableLayout: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableLayout = view.findViewById(R.id.table_layout)

        val tableData = getDataForTable()

        //  addHeaderRow() // Добавляем шапку таблицы

        for (item in tableData) {
            addRow(item) // Добавляем каждую строку данных
        }
    }

    private fun getDataForTable(): List<TableItem> {
        val data: MutableList<TableItem> = mutableListOf()

        val item1 = TableItem(
            time = "10:00",
            sensor = "Датчик 1",
            sensorDescription = "Описание датчика 1",
            actualValue = "Значение 1",
            expectedValue = "Ожидаемое значение 1",
            alarmMessage = "Сообщение об аварии 1",
            recommendationMessage = "Сообщение с рекомендацией 1"
        )

        data.add(item1)
        data.add(item1)
        data.add(item1)
        data.add(item1)
        data.add(item1)
        data.add(item1)
        return data
    }

    private fun addHeaderRow() {
        val headerRow = TableRow(requireContext())

        val timeTextView = TextView(requireContext())
        timeTextView.text = "Время"
        timeTextView.textSize = 16f
        timeTextView.setSingleLine(true)
        headerRow.addView(timeTextView)

        val sensorTextView = TextView(requireContext())
        sensorTextView.text = "Датчик"
        sensorTextView.textSize = 16f
        sensorTextView.setSingleLine(true)
        headerRow.addView(sensorTextView)

        val sensorDescriptionTextView = TextView(requireContext())
        sensorDescriptionTextView.text = "Описание датчика"
        sensorDescriptionTextView.textSize = 16f
        sensorDescriptionTextView.setSingleLine(true)
        headerRow.addView(sensorDescriptionTextView)

        val actualValueTextView = TextView(requireContext())
        actualValueTextView.text = "Актуальное значение"
        actualValueTextView.textSize = 16f
        actualValueTextView.setSingleLine(true)
        headerRow.addView(actualValueTextView)

        val expectedValueTextView = TextView(requireContext())
        expectedValueTextView.text = "Граница значения"
        expectedValueTextView.textSize = 16f
        expectedValueTextView.setSingleLine(true)
        headerRow.addView(expectedValueTextView)

        val alarmMessageTextView = TextView(requireContext())
        alarmMessageTextView.text = "Сообщение"
        alarmMessageTextView.textSize = 16f
        alarmMessageTextView.setSingleLine(true)
        headerRow.addView(alarmMessageTextView)

        val recommendationMessageTextView = TextView(requireContext())
        recommendationMessageTextView.text = "Рекомендации"
        recommendationMessageTextView.textSize = 16f
        recommendationMessageTextView.setSingleLine(true)
        headerRow.addView(recommendationMessageTextView)

        tableLayout.addView(headerRow)
    }

    private fun addRow(item: TableItem) {
        val row = TableRow(requireContext())

        val timeTextView = TextView(requireContext())
        timeTextView.text = item.time
        timeTextView.textSize = 18F
        timeTextView.maxWidth = 1000
        timeTextView.gravity = Gravity.CENTER
        timeTextView.setPadding(4, 4, 4, 4)
        row.addView(timeTextView)

        val sensorTextView = TextView(requireContext())
        sensorTextView.text = item.sensor
        sensorTextView.textSize = 18F
        sensorTextView.maxWidth = 1000
        sensorTextView.gravity = Gravity.CENTER
        sensorTextView.setPadding(4, 4, 4, 4)
        row.addView(sensorTextView)

        val sensorDescriptionTextView = TextView(requireContext())
        sensorDescriptionTextView.text = item.sensorDescription
        sensorDescriptionTextView.textSize = 18F
        sensorDescriptionTextView.maxWidth = 1000
        sensorDescriptionTextView.gravity = Gravity.CENTER
        sensorDescriptionTextView.setPadding(4, 4, 4, 4)
        row.addView(sensorDescriptionTextView)

        val actualValueTextView = TextView(requireContext())
        actualValueTextView.text = item.actualValue
        actualValueTextView.textSize = 18F
        actualValueTextView.maxWidth = 1000
        actualValueTextView.gravity = Gravity.CENTER
        actualValueTextView.setPadding(4, 4, 4, 4)
        row.addView(actualValueTextView)

        val expectedValueTextView = TextView(requireContext())
        expectedValueTextView.text = item.expectedValue
        expectedValueTextView.textSize = 18F
        expectedValueTextView.maxWidth = 1000
        expectedValueTextView.gravity = Gravity.CENTER
        expectedValueTextView.setPadding(4, 4, 4, 4)
        row.addView(expectedValueTextView)

        val alarmMessageTextView = TextView(requireContext())
        alarmMessageTextView.text = item.alarmMessage
        alarmMessageTextView.textSize = 18F
        alarmMessageTextView.maxWidth = 1000
        alarmMessageTextView.gravity = Gravity.CENTER
        alarmMessageTextView.setPadding(4, 4, 4, 4)
        row.addView(alarmMessageTextView)

        val recommendationMessageTextView = TextView(requireContext())
        recommendationMessageTextView.text = item.recommendationMessage
        recommendationMessageTextView.textSize = 18F
        recommendationMessageTextView.maxWidth = 1000
        recommendationMessageTextView.gravity = Gravity.CENTER
        recommendationMessageTextView.setPadding(4, 4, 4, 4)
        row.addView(recommendationMessageTextView)

        tableLayout.addView(row)
    }
}