package ru.example.alarmmonitoring.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.example.alarmmonitoring.data.HomeTableLine
import ru.example.alarmmonitoring.databinding.HomeTableLineItemBinding

class HomeTableLineAdapter : RecyclerView.Adapter<HomeTableLineAdapter.HomeTableLineViewHolder>() {

    var data = listOf<HomeTableLine>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTableLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeTableLineItemBinding.inflate(inflater, parent, false)
        return HomeTableLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTableLineViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class HomeTableLineViewHolder(private val binding: HomeTableLineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeTableLine) {
            binding.homeTableLine = item
            binding.executePendingBindings()
        }
    }
}