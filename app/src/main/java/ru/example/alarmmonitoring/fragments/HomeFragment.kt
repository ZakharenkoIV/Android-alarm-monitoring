package ru.example.alarmmonitoring.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.alarmmonitoring.MainActivity
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.adapter.HomeTableLineAdapter
import ru.example.alarmmonitoring.databinding.FragmentHomeBinding
import ru.example.alarmmonitoring.fragments.viewmodel.HomeViewModel
import ru.example.alarmmonitoring.fragments.viewmodel.SettingViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var settingViewModel: SettingViewModel
    private val homeTableLineAdapter = HomeTableLineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupButtons()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = (requireActivity() as MainActivity).getHomeViewModel()
        settingViewModel = (requireActivity() as MainActivity).getSettingViewModel()
        binding.homeViewModel = homeViewModel
        binding.settingViewModel = settingViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = homeTableLineAdapter

        homeViewModel.tableData.observe(viewLifecycleOwner) { data ->
            homeTableLineAdapter.data = data
        }
    }

    private fun setupButtons() {
        binding.messagesButton.setOnClickListener {
            val actionId = R.id.action_homeFragment_to_logFragment
            findNavController().navigate(actionId)
        }

        binding.settingsButton.setOnClickListener {
            val actionId = R.id.action_homeFragment_to_settingFragment
            findNavController().navigate(actionId)
        }
    }
}