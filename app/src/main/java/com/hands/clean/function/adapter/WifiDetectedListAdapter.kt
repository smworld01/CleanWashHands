package com.hands.clean.function.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.wifi.WifiDetectedViewModel
import com.hands.clean.function.room.entry.WifiEntry

class WifiDetectedListAdapter(private val viewModel: WifiDetectedViewModel) : ListAdapter<WifiEntry, RecyclerView.ViewHolder>(
    DateCountDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_wifi_detected, parent, false)
        return RecyclerItem(view, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))
    }

    class RecyclerItem(itemView: View, private val viewModel: WifiDetectedViewModel) : RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView = itemView.findViewById(R.id.textViewDeviceName)
        var button: Button = itemView.findViewById(R.id.button)
        fun bind (data: WifiEntry) {
            deviceName.text = data.deviceName

            button.setOnClickListener {
                viewModel.createWifiEntry.value = data
            }
        }
    }

    companion object {
        object DateCountDiffCallback : DiffUtil.ItemCallback<WifiEntry>() {
            override fun areItemsTheSame(oldItem: WifiEntry, newItem: WifiEntry): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: WifiEntry, newItem: WifiEntry): Boolean {
                return true
            }
        }
    }
}