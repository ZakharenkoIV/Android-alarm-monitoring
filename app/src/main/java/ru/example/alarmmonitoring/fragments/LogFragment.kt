package ru.example.alarmmonitoring.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.example.alarmmonitoring.MainActivity
import ru.example.alarmmonitoring.adapter.LogTableLineAdapter
import ru.example.alarmmonitoring.databinding.FragmentLogBinding
import ru.example.alarmmonitoring.fragments.viewmodel.LogViewModel

class LogFragment : Fragment() {
    private lateinit var logViewModel: LogViewModel
    private lateinit var binding: FragmentLogBinding
    private val logTableLineAdapter = LogTableLineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logViewModel = (requireActivity() as MainActivity).getLogViewModel()
        binding.viewModel = logViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = logTableLineAdapter

        logViewModel.tableData.observe(viewLifecycleOwner) { data ->
            logTableLineAdapter.data = data
        }
    }
}
