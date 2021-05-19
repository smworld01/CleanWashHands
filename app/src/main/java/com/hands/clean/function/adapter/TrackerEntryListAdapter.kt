package com.hands.clean.function.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.TrackerListViewModel
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import kotlin.concurrent.thread

class TrackerEntryListAdapter(private val viewModel: TrackerListViewModel) : ListAdapter<TrackerEntry, RecyclerView.ViewHolder>(
    TrackerEntry.Companion.DateCountDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_tracker, parent, false)
        return RecyclerItem(view, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))



    }

    class RecyclerItem(itemView: View, private val viewModel: TrackerListViewModel) : RecyclerView.ViewHolder(itemView) {
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
                viewModel.searchTrackerEntry.value = data
            }

            textViewDeviceName.text = data.name
            switchNotification.isChecked = data.isNotification

            switchNotification.setOnCheckedChangeListener { _, isChecked ->
                thread {
                    data.isNotification = true
                    when(data) {
                        is BluetoothEntry -> DB.getInstance().bluetoothDao().updateAll(data)
                        is WifiEntry -> DB.getInstance().wifiDao().updateAll(data)
                        is GpsEntry -> DB.getInstance().gpsDao().updateAll(data)
                    }
                }
            }
        }
    }
}