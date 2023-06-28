package ru.example.alarmmonitoring

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.example.alarmmonitoring.fragments.FirstFragment
import ru.example.alarmmonitoring.fragments.SecondFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        showFirstFragment()

    }

    private fun showFirstFragment() {
        val fragment = FirstFragment()
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun showSecondFragment() {
        val fragment = SecondFragment()
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Добавление транзакции в стек возврата
        transaction.commit()
    }
}