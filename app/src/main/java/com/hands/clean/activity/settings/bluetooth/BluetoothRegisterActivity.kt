package com.hands.clean.activity.settings.bluetooth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.dialog.DeviceRegisterDialog
import com.hands.clean.function.adapter.BluetoothDeviceAdapter

class BluetoothRegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: BluetoothRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_register)

        initViewModel()

        initRecyclerView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(BluetoothRegisterViewModel::class.java)
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewBluetoothDevice)
        val lm = LinearLayoutManager(this)

        recyclerView.apply {
            adapter = BluetoothDeviceAdapter(viewModel)
            setHasFixedSize(true)
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }

        viewModel.createBluetoothEntry.observe(this) {
            if (it != null) {
                val dialog = DeviceRegisterDialog(it)
                dialog.show(this.supportFragmentManager, dialog.tag)
            }
        }
    }
}