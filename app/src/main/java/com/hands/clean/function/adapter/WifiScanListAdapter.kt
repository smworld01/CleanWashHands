package com.hands.clean.function.adapter

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.wifi.WifiScanViewModel
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*

class WifiScanListAdapter(private val viewModel: WifiScanViewModel) : ListAdapter<ScanResult, RecyclerView.ViewHolder>(
    DateCountDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_wifi_scan, parent, false)
        return RecyclerItem(view, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))
    }

    class RecyclerItem(itemView: View, private val viewModel: WifiScanViewModel) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val deviceName: TextView = itemView.findViewById(R.id.textViewDeviceName)
        var button: Button = itemView.findViewById(R.id.button)
        fun bind (data: ScanResult) {
            val capabilities = data.capabilities
            val drawable = if (capabilities.contains("WPA") || capabilities.contains("WEP"))
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_baseline_signal_wifi_4_bar_lock_24, null)
            else
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_baseline_signal_wifi_4_bar_24, null)

            icon.setImageDrawable(drawable)

            deviceName.text = data.SSID

            button.setOnClickListener {
                val findWifiEntry = DB.getInstance().wifiDao().findByAddress(data.BSSID)
                if(findEntryIsExist(findWifiEntry)) {
                    Toast.makeText(
                        itemView.context, "이미 등록된 와이파이입니다.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.createWifiEntry.value = WifiEntry(
                        0,
                        "",
                        data.SSID,
                        data.BSSID,
                        true
                    )
                }
            }
        }
        private fun findEntryIsExist(findEntry: WifiEntry?): Boolean {
            return findEntry != null
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