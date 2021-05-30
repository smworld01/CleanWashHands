package com.hands.clean.activity.settings.bluetooth

import android.os.Bundle
import android.view.MenuItem
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

        initActionBar()

        initViewModel()

        initRecyclerView()
    }

    private fun initActionBar() {
        supportActionBar?.title = "블루투스 등록"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    // 상단바 뒤로가기 버튼 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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