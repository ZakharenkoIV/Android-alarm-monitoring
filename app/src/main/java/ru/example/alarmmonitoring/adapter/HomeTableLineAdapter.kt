package ru.example.alarmmonitoring.adapter

import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.data.HomeTableLine

class HomeTableLineAdapter(private val tableLayout: TableLayout) {

    var data = listOf<HomeTableLine>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun notifyDataSetChanged() {
        tableLayout.removeAllViews()
        if (!hasHeader()) {
            addHeaders()
        }
        addData()
    }

    private fun addHeaders() {
        val headerRow = TableRow(tableLayout.context)
        headerRow.addView(noHorScroll(setBackgroundHeader(createTextView("№"))))
        headerRow.addView(noHorScroll(setBackgroundHeader(createTextView("Время"))))
        headerRow.addView(noHorScroll(setBackgroundHeader(createTextView("Датчик"))))
        headerRow.addView(noHorScroll(setBackgroundHeader(createTextView("Значение"))))
        headerRow.addView(noHorScroll(setBackgroundHeader(createTextView("Сообщение"))))
        tableLayout.addView(headerRow)
    }

    private fun noHorScroll(view: TextView): TextView {
        view.setHorizontallyScrolling(false)
        return view
    }

//    private fun noVerScroll(view: TextView): TextView {
//        view.ver .setVerHorizontallyScrolling(false)
//        return view
//    }

    private fun hasHeader(): Boolean {
        return tableLayout.childCount > 0
    }

    private fun addData() {
        // Если шапка уже есть, начинаем добавлять данные со второго элемента
        val startIndex = 0

        for (i in startIndex until data.size) {
            val item = data[i]
            val dataRow = TableRow(tableLayout.context)
            dataRow.addView(setBackgroundHeader(createTextView((i + 1).toString())))
            dataRow.addView(setBackgroundTextView(createTextView(item.time), item.signalClass))
            dataRow.addView(setBackgroundTextView(createTextView(item.sensor), item.signalClass))
            dataRow.addView(setBackgroundTextView(createTextView(item.value), item.signalClass))
            dataRow.addView(
                setBackgroundTextView(
                    createTextView(item.alarmMessage),
                    item.signalClass
                )
            )

            // Добавляем новую строку только после шапки, если она уже есть
            if (hasHeader()) {
                tableLayout.addView(dataRow, i + 1)
            } else {
                tableLayout.addView(dataRow)
            }
        }
    }

    private fun setBackgroundTextView(view: TextView, signalClass: String): TextView {
        val context = view.context
        val backgroundColor: ColorDrawable
        when (signalClass) {
            "0" -> {
                backgroundColor = ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.color_signal_class_0
                    )
                )
                view.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            "1" -> {
                backgroundColor = ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.color_signal_class_1
                    )
                )
                view.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            "2" -> {
                backgroundColor = ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.color_signal_class_2
                    )
                )
                view.setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            else -> {
                backgroundColor = ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.color_signal_class_def
                    )
                )
                view.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
        val border = getBorder(
            ContextCompat.getColor(
                context,
                R.color.black
            )
        )
        val layerDrawable = LayerDrawable(arrayOf(backgroundColor, border))

        view.background = layerDrawable
        return view
    }



    private fun setBackgroundHeader(view: TextView): TextView {
        val context = view.context
        val backgroundColor = ColorDrawable(
            ContextCompat.getColor(
                context,
                R.color.color_background_header
            )
        )
        val border = getBorder(
            ContextCompat.getColor(
                context,
                R.color.black
            )
        )
        val layerDrawable = LayerDrawable(arrayOf(backgroundColor, border))

        view.background = layerDrawable
        view.setTextColor(ContextCompat.getColor(context, R.color.black))
        return view
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(tableLayout.context)
        textView.text = text
        textView.setPadding(20, 10, 20, 10)
        return textView
    }

    private fun getBorder(borderColor: Int): ShapeDrawable {
        val border = ShapeDrawable()
        border.paint.color = borderColor
        val borderWidth = 2
        border.paint.strokeWidth = borderWidth.toFloat()
        border.paint.style = Paint.Style.STROKE
        return border
    }
}