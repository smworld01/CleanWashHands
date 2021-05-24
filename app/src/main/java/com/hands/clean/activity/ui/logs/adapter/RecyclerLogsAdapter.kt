package com.hands.clean.activity.ui.logs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entry.WashEntry

class RecyclerLogsAdapter(
    private val washData: List<WashEntry>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var date: TextView = itemView.findViewById(R.id.logsDate)
        private var detail: TextView = itemView.findViewById(R.id.logsDetail)
        private val image: ImageView = itemView.findViewById(R.id.logsImage)

        fun bind (data: WashEntry) {
            date.text = data.date
            detail.text = data.detail

            val draw = when (data.type) {
                "Bluetooth" ->
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_baseline_bluetooth_24, null)
                "WiFi" ->
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_baseline_wifi_24, null)
                "GPS" ->
                    ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_baseline_gps_fixed_24, null)
                else -> throw Exception()
            }

            image.setImageDrawable(draw)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerWashItem(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_item_logs, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
        recyclerWashItem.bind(washData[position])
    }

    override fun getItemCount(): Int {
        return washData.size
    }
}