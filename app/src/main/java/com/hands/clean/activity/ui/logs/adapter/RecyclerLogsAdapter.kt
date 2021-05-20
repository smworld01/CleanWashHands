package com.hands.clean.activity.ui.logs.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entry.WashEntry

class RecyclerLogsAdapter(private val washData: List<WashEntry>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class RecyclerWashItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById<TextView>(R.id.logsDate)
        var detail: TextView = itemView.findViewById<TextView>(R.id.logsDetail)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.logsImage)

        fun bind (data: WashEntry) {
            date.text = data.date
            detail.text = data.detail
            if(data.type == "Bluetooth") image.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_bluetooth_24, null))
            if(data.type == "WiFi") image.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_wifi_24, null))
            if(data.type == "GPS") image.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_gps_fixed_24, null))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        val holder:RecyclerView.ViewHolder
        view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_logs, parent, false)
        holder = RecyclerWashItem(view)

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerWashItem: RecyclerWashItem = holder as RecyclerWashItem
        recyclerWashItem.bind(washData[position])
    }

    override fun getItemCount(): Int {
        return washData.size
    }
}