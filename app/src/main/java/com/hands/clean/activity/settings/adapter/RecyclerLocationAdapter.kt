package com.hands.clean.activity.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import kotlin.concurrent.thread

class RecyclerDeviceAdapter (private val RecyclerDeviceData :List<LocationEntry>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class RecyclerDeviceItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var switchDevice: SwitchCompat = itemView.findViewById(R.id.switchDevice)

        fun bind (data: LocationEntry) {
            switchDevice.text = data.name
            switchDevice.isChecked = data.isNotification

            switchDevice.setOnCheckedChangeListener { _, isChecked ->
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
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.device_recycler_item, parent, false)
        return RecyclerDeviceItem(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerDeviceItem: RecyclerDeviceItem = holder as RecyclerDeviceItem
        recyclerDeviceItem.bind(RecyclerDeviceData[position])
    }

    override fun getItemCount(): Int = RecyclerDeviceData.size
}

fun adaptRecyclerDevice(context: Context, recyclerView: RecyclerView, arrayList: List<LocationEntry>): RecyclerView {
    val mAdapter = RecyclerDeviceAdapter(arrayList)
    val lm = LinearLayoutManager(context)

    recyclerView.apply {
        setHasFixedSize(true)
        adapter = mAdapter
        layoutManager = lm
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
        addItemDecoration(itemDecoration)
    }
    return recyclerView
}