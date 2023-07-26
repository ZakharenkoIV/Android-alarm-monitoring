package ru.example.alarmmonitoring

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.example.alarmmonitoring.database.MyDatabaseProvider
import ru.example.alarmmonitoring.database.dao.LogTableDao
import ru.example.alarmmonitoring.fragments.viewmodel.HomeViewModel
import ru.example.alarmmonitoring.fragments.viewmodel.LogViewModel
import ru.example.alarmmonitoring.fragments.viewmodel.SettingViewModel
import ru.example.alarmmonitoring.integration.MessageController

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var logViewModel: LogViewModel
    private lateinit var logTableDao: LogTableDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController)

        val databaseProvider = MyDatabaseProvider(application)
        logTableDao = databaseProvider.provideMyDataDao()

        loadFromDataViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun loadFromDataViewModel() {
        val messageController = MessageController(logTableDao)

        if (!::homeViewModel.isInitialized) {
            homeViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[HomeViewModel::class.java]
            homeViewModel.setMessageController(messageController)
        }
        if (!::settingViewModel.isInitialized) {
            settingViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[SettingViewModel::class.java]
            settingViewModel.setMessageController(messageController)
        }
        if (!::logViewModel.isInitialized) {
            logViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[LogViewModel::class.java]
            logViewModel.setMessageController(messageController)
        }
    }

    fun getSettingViewModel(): SettingViewModel {
        return settingViewModel
    }

    fun getHomeViewModel(): HomeViewModel {
        return homeViewModel
    }

    fun getLogViewModel(): LogViewModel {
        return logViewModel
    }
}