package ru.example.alarmmonitoring.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var connectButton1: Button
    private lateinit var connectButton2: Button
    private lateinit var connectButton3: Button
    private lateinit var messageTextView: TextView
    private lateinit var messagesButton: Button
    private lateinit var settingsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
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
            val thirdFragment = ThirdFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, thirdFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        settingsButton.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val existingFragment = fragmentManager.findFragmentByTag("SECOND_FRAGMENT")

            if (existingFragment == null) {
                val fragment = SecondFragment()
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment, "SECOND_FRAGMENT")
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun setupConnectButtons() {
        connectButton1.setBackgroundResource(R.drawable.not_work_circle)
        connectButton2.setBackgroundResource(R.drawable.not_work_circle)
        connectButton3.setBackgroundResource(R.drawable.not_work_circle)

        connectButton1.setOnClickListener {
            connectButton1.setBackgroundResource(R.drawable.loading_circle)
        }

        connectButton2.setOnClickListener {
            connectButton2.setBackgroundResource(R.drawable.loading_circle)
        }

        connectButton3.setOnClickListener {
            connectButton3.setBackgroundResource(R.drawable.loading_circle)
        }
    }
}