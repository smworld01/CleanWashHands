package com.hands.clean.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R

class RecyclerDeviceAdapter (private val RecyclerDeviceData :ArrayList<RecyclerDeviceData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class RecyclerDeviceItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deviceID: TextView = itemView.findViewById<TextView>(R.id.deviceID)

        fun bind (data: RecyclerDeviceData) {
            deviceID.text = data.deviceID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        val holder:RecyclerView.ViewHolder
        view = LayoutInflater.from(parent.context).inflate(R.layout.device_recycler_item, parent, false)
        holder = RecyclerDeviceItem(view)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerDeviceItem: RecyclerDeviceItem = holder as RecyclerDeviceItem
        recyclerDeviceItem.bind(RecyclerDeviceData[position])
    }

    override fun getItemCount(): Int = RecyclerDeviceData.size

}