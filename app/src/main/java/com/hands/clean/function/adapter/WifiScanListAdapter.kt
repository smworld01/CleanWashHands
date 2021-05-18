package com.hands.clean.function.adapter

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import kotlin.concurrent.thread

class WifiScanListAdapter : ListAdapter<ScanResult, RecyclerView.ViewHolder>(
    DateCountDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_wifi_scan, parent, false)
        return RecyclerItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))
    }

    class RecyclerItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val deviceName: TextView = itemView.findViewById(R.id.textViewDeviceName)
        var button: Button = itemView.findViewById(R.id.button)
        fun bind (data: ScanResult) {
            val capabilities = data.capabilities
            val drawable = if (capabilities.contains("WPA") || capabilities.contains("WEP"))
                itemView.resources.getDrawable(R.drawable.ic_baseline_signal_wifi_4_bar_lock_24, null)
            else
                itemView.resources.getDrawable(R.drawable.ic_baseline_signal_wifi_4_bar_24, null)

            icon.setImageDrawable(drawable)

            deviceName.text = data.SSID
        }
    }

    companion object {
        object DateCountDiffCallback : DiffUtil.ItemCallback<ScanResult>() {
            override fun areItemsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
                return oldItem.BSSID == newItem.BSSID
            }

            override fun areContentsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
                return true
            }
        }
    }
}