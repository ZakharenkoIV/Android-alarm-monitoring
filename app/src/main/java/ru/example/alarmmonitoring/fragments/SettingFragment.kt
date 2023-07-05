package ru.example.alarmmonitoring.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import ru.example.alarmmonitoring.R

class SettingFragment : Fragment() {
    private lateinit var toggleSwitch: Switch
    private lateinit var connectButton1: Button
    private lateinit var connectButton2: Button
    private lateinit var connectButton3: Button
    private lateinit var editText: EditText
    private lateinit var engineerRadioGroup: RadioGroup
    private lateinit var engineer1RadioButton: RadioButton
    private lateinit var engineer2RadioButton: RadioButton
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        initViews(view)

        val pollValue = sharedPreferences.getBoolean("poll", false)
        toggleSwitch.isChecked = pollValue

        when (sharedPreferences.getInt("activeEngineer", 1)) {
            1 -> engineer1RadioButton.isChecked = true
            2 -> engineer2RadioButton.isChecked = true
        }

        toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onToggleSwitchEnabled()
            } else {
                onToggleSwitchDisabled()
            }
        }

        connectButton1.setOnClickListener {
            if (sharedPreferences.getBoolean("poll", false)) {
                onConnectButton1Click()
            }
        }

        connectButton2.setOnClickListener {
            if (sharedPreferences.getBoolean("poll", false)) {
                onConnectButton2Click()
            }
        }

        connectButton3.setOnClickListener {
            if (sharedPreferences.getBoolean("poll", false)) {
                onConnectButton3Click()
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                onEditTextChanged(s.toString())
            }
        })

        engineerRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.engineer1_radio_button -> onEngineer1RadioButtonChecked()
                R.id.engineer2_radio_button -> onEngineer2RadioButtonChecked()
            }
        }

        return view
    }

    private fun initViews(view: View) {
        toggleSwitch = view.findViewById(R.id.toggle_switch)
        connectButton1 = view.findViewById(R.id.connect_button_1)
        setButtonState(connectButton1, "button_1_state")

        connectButton2 = view.findViewById(R.id.connect_button_2)
        setButtonState(connectButton2, "button_2_state")

        connectButton3 = view.findViewById(R.id.connect_button_3)
        setButtonState(connectButton3, "button_3_state")

        editText = view.findViewById(R.id.edit_text_field)
        engineerRadioGroup = view.findViewById(R.id.engineer_radio_group)
        engineer1RadioButton = view.findViewById(R.id.engineer1_radio_button)
        engineer2RadioButton = view.findViewById(R.id.engineer2_radio_button)
    }

    private fun onToggleSwitchEnabled() {
        editor.putBoolean("poll", true)
        editor.apply()
    }

    private fun onToggleSwitchDisabled() {
        editor.putBoolean("poll", false)
        editor.apply()
        connectButton1.setBackgroundResource(R.drawable.not_work_circle)
        editor.putString("button_1_state", "not_work_circle")
        connectButton2.setBackgroundResource(R.drawable.not_work_circle)
        editor.putString("button_2_state", "not_work_circle")
        connectButton3.setBackgroundResource(R.drawable.not_work_circle)
        editor.putString("button_3_state", "not_work_circle")
        editor.apply()
    }

    private fun setButtonState(button: Button, stateKey: String) {
        val state = sharedPreferences.getString(stateKey, "loss_of_contact_circle")
        val drawableResId = getDrawableResId(state)
        button.setBackgroundResource(drawableResId)
    }

    private fun getDrawableResId(state: String?): Int {
        return when (state) {
            "loading_circle" -> R.drawable.loading_circle
            "not_work_circle" -> R.drawable.not_work_circle
            "ok_circle" -> R.drawable.ok_circle
            "pause_circle" -> R.drawable.pause_circle
            else -> R.drawable.loss_of_contact_circle
        }
    }

    private fun onConnectButton1Click() {
        val state = sharedPreferences.getString("button_1_state", "loss_of_contact_circle")
        if ("loading_circle" == state) {
            //логика при нажатии на загрузку сервера
        } else if ("not_work_circle" == state) {
            connectButton1.setBackgroundResource(getDrawableResId("loading_circle"))
            editor.putString("button_1_state", "loading_circle")
            editor.apply()
        } else if ("ok_circle" == state) {
            //логика при нажатии на работающий сервер
        } else if ("pause_circle" == state) {
            //логика при нажатии на сервер на паузе
        } else if ("loss_of_contact_circle" == state) {
            //логика при нажатии на неисправный сервер
        }
    }

    private fun onConnectButton2Click() {
        val state = sharedPreferences.getString("button_2_state", "loss_of_contact_circle")
        if ("loading_circle" == state) {
            //логика при нажатии на загрузку сервера
        } else if ("not_work_circle" == state) {
            connectButton2.setBackgroundResource(getDrawableResId("loading_circle"))
            editor.putString("button_2_state", "loading_circle")
            editor.apply()
        } else if ("ok_circle" == state) {
            //логика при нажатии на работающий сервер
        } else if ("pause_circle" == state) {
            //логика при нажатии на сервер на паузе
        } else if ("loss_of_contact_circle" == state) {
            //логика при нажатии на неисправный сервер
        }
    }

    private fun onConnectButton3Click() {
        val state = sharedPreferences.getString("button_3_state", "loss_of_contact_circle")
        if ("loading_circle" == state) {
            //логика при нажатии на загрузку сервера
        } else if ("not_work_circle" == state) {
            connectButton3.setBackgroundResource(getDrawableResId("loading_circle"))
            editor.putString("button_3_state", "loading_circle")
            editor.apply()
        } else if ("ok_circle" == state) {
            //логика при нажатии на работающий сервер
        } else if ("pause_circle" == state) {
            //логика при нажатии на сервер на паузе
        } else if ("loss_of_contact_circle" == state) {
            //логика при нажатии на неисправный сервер
        }
    }


    private fun onEditTextChanged(text: String) {
        // Обработка изменения текста в editText
    }

    private fun onEngineer1RadioButtonChecked() {
        editor.putInt("activeEngineer", 1)
        editor.apply()
    }

    private fun onEngineer2RadioButtonChecked() {
        editor.putInt("activeEngineer", 2)
        editor.apply()
    }
}