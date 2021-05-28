package com.hands.clean.function.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entry.WashEntry

class RecyclerLogsAdapter : ListAdapter<WashEntry, RecyclerView.ViewHolder>(
    DiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_logs, parent, false)
        return RecyclerItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(getItem(position))
    }

    class RecyclerItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var layout: LinearLayout = itemView.findViewById(R.id.itemLayout)
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

            val color = ContextCompat.getColor(itemView.context,
                if (data.wash) R.color.logs_wash
                else R.color.logs_don_wash
            )
            layout.setBackgroundColor(color)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<WashEntry>() {
        override fun areItemsTheSame(oldItem: WashEntry, newItem: WashEntry): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: WashEntry, newItem: WashEntry): Boolean {
            return oldItem.wash == newItem.wash
        }
    }
}