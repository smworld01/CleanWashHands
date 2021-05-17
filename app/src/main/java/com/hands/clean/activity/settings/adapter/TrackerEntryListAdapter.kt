package com.hands.clean.activity.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import kotlin.concurrent.thread

class TrackerEntryListAdapter() : ListAdapter<TrackerEntry, RecyclerView.ViewHolder>(TrackerEntry.Companion.DateCountDiffCallback) {
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutItem: LinearLayout = itemView.findViewById(R.id.layoutItem)
        val textViewDeviceName: TextView = itemView.findViewById(R.id.textViewDeviceName)
        var switchNotification: SwitchCompat = itemView.findViewById(R.id.switchNotification)

        fun bind (data: TrackerEntry) {
            layoutItem.setOnLongClickListener {
                when(data) {
                    is BluetoothEntry -> DB.getInstance().bluetoothDao().delete(data)
                    is WifiEntry -> DB.getInstance().wifiDao().delete(data)
                    is GpsEntry -> DB.getInstance().gpsDao().delete(data)
                }

                return@setOnLongClickListener true
            }
            layoutItem.setOnClickListener {
                switchNotification.isChecked = !switchNotification.isChecked
            }

            textViewDeviceName.text = data.name
            switchNotification.isChecked = data.isNotification

            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                thread {
                    when(data) {
                        is BluetoothEntry -> DB.getInstance().bluetoothDao().changeNotificationByAddress(data.address, isChecked)
                        is WifiEntry -> DB.getInstance().matchDaoByEntry(data).changeNotificationByAddress(data.address, isChecked)
                        is GpsEntry -> DB.getInstance().gpsDao().changeNotificationByRequestId(data.requestId, isChecked)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.device_recycler_item, parent, false)
        return RecyclerWashItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
        recyclerWashItem.bind(getItem(position))
    }
}