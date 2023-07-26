package ru.example.alarmmonitoring.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.example.alarmmonitoring.data.LogTableLine
import ru.example.alarmmonitoring.databinding.LogTableLineItemBinding

class LogTableLineAdapter : RecyclerView.Adapter<LogTableLineAdapter.LogTableLineViewHolder>() {

    var data = listOf<LogTableLine>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogTableLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LogTableLineItemBinding.inflate(inflater, parent, false)
        return LogTableLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogTableLineViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class LogTableLineViewHolder(private val binding: LogTableLineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LogTableLine) {
            binding.logTableLine = item
            binding.executePendingBindings()
        }
    }
}