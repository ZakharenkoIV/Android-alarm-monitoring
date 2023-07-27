package ru.example.alarmmonitoring.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import ru.example.alarmmonitoring.MainActivity
import ru.example.alarmmonitoring.R
import ru.example.alarmmonitoring.adapter.LogTableLineAdapter
import ru.example.alarmmonitoring.databinding.FragmentLogBinding
import ru.example.alarmmonitoring.fragments.viewmodel.LogViewModel

class LogFragment : Fragment() {
    private lateinit var logViewModel: LogViewModel
    private lateinit var binding: FragmentLogBinding
    private lateinit var logTableLineAdapter: LogTableLineAdapter

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

        val tableLayout = view.findViewById<TableLayout>(R.id.tableLayout)
        logTableLineAdapter = LogTableLineAdapter(tableLayout)

        logViewModel.tableData.observe(viewLifecycleOwner) { data ->
            logTableLineAdapter.data = data
        }
    }
}
