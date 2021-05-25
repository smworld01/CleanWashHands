package com.hands.clean.function.adapter

import android.net.wifi.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.gps.MapsViewModel
import com.hands.clean.activity.settings.wifi.WifiScanViewModel
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*

class GpsListAdapter(private val viewModel: MapsViewModel) : ListAdapter<TrackerEntry, RecyclerView.ViewHolder>(
    TrackerEntry.Companion.DateCountDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_gps, parent, false)
        return RecyclerItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))
    }

    class RecyclerItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView.findViewById(R.id.layoutItem)
        val deviceName: TextView = itemView.findViewById(R.id.deviceName)

        fun bind (data: TrackerEntry) {
            deviceName.text = data.name
        }
    }
}