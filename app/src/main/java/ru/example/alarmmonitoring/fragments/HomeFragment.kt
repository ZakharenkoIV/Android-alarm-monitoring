package ru.example.alarmmonitoring.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var connectButton1: Button
    private lateinit var connectButton2: Button
    private lateinit var connectButton3: Button
    private lateinit var messageTextView: TextView
    private lateinit var messagesButton: Button
    private lateinit var settingsButton: Button

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "button_state") {
                updateConnectButtons()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectButton1 = binding.connectButton1
        connectButton2 = binding.connectButton2
        connectButton3 = binding.connectButton3
        messageTextView = binding.messageBoard
        messagesButton = binding.messagesButton
        settingsButton = binding.settingsButton

        setupConnectButtons()

        messagesButton.setOnClickListener {
            val actionId = R.id.action_homeFragment_to_logFragment
            findNavController().navigate(actionId)
        }

        settingsButton.setOnClickListener {
            val actionId = R.id.action_homeFragment_to_settingFragment
            findNavController().navigate(actionId)
        }

        // Добавляем наблюдателя (Observer) для отслеживания изменений в SharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        // Обновляем кнопки при создании фрагмента
        updateConnectButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Удаляем наблюдателя (Observer) при уничтожении представления фрагмента
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    private fun setupConnectButtons() {
        // Оставляем метод без изменений
    }

    private fun updateConnectButtons() {
        setButtonState(connectButton1, "button_1_state")
        setButtonState(connectButton2, "button_2_state")
        setButtonState(connectButton3, "button_3_state")
    }

    private fun setButtonState(button: Button, stateKey: String) {
        if (sharedPreferences.getBoolean("poll", false)) {
            val drawableResId =
                when (sharedPreferences.getString(stateKey, "loss_of_contact_circle")) {
                    "loading_circle" -> R.drawable.loading_circle
                    "not_work_circle" -> R.drawable.not_work_circle
                    "ok_circle" -> R.drawable.ok_circle
                    "pause_circle" -> R.drawable.pause_circle
                    else -> R.drawable.loss_of_contact_circle
                }
            button.setBackgroundResource(drawableResId)
        } else {
            button.setBackgroundResource(R.drawable.not_work_circle)
        }
    }
}