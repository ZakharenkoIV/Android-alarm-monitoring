package ru.example.alarmmonitoring.adapter

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import ru.example.alarmmonitoring.data.LogTableLine

class LogTableLineAdapter(private val tableLayout: TableLayout) {

    var data = listOf<LogTableLine>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun notifyDataSetChanged() {
        tableLayout.removeAllViews()
        addData()
    }

    private fun addData() {
        for (item in data) {
            val dataRow = TableRow(tableLayout.context)
            dataRow.addView(createTextView(item.time))
            dataRow.addView(createTextView(item.sensor))
            dataRow.addView(createTextView(item.sensorDescription))
            dataRow.addView(createTextView(item.actualValue))
            dataRow.addView(createTextView(item.boundaryValue))
            dataRow.addView(createTextView(item.alarmMessage))
            tableLayout.addView(dataRow)
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(tableLayout.context)
        textView.text = text
        textView.setPadding(20, 10, 20, 10)

        // Создаем объект ShapeDrawable для установки границ
        val border = ShapeDrawable()
        // Устанавливаем цвет границы
        border.paint.color = Color.BLACK
        // Устанавливаем толщину границы в пикселях
        val borderWidth = 2
        border.paint.strokeWidth = borderWidth.toFloat()
        // Устанавливаем стиль границы (SOLID - сплошная линия)
        border.paint.style = Paint.Style.STROKE
        // Устанавливаем объект ShapeDrawable в качестве фона у TextView
        textView.background = border
        return textView
    }
}