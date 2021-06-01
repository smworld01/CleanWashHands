package com.hands.clean.activity.settings.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hands.clean.R
import com.hands.clean.dialog.DeviceRegisterDialog
import com.hands.clean.function.adapter.WifiScanListAdapter
import com.hands.clean.function.gps.SystemSettingsGpsManager

class WifiScanActivity : AppCompatActivity() {
    private lateinit var wifiManager: WifiManager
    private lateinit var viewModel: WifiScanViewModel
    private lateinit var wifiScanReceiver: WifiScanReceiver

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_scan)

        initActionBar()

        initSwipeRefresh()

        initWifiScan()

        initRecyclerView()

        val gpsManager = SystemSettingsGpsManager(this)

        gpsManager.registerDisableCallBack {
            Toast.makeText(this, "위치 서비스가 활성화되지 않았습니다.", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        gpsManager.registerCancelCallback { finish() }
        gpsManager.onRequest()
    }

    private fun initActionBar() {
        supportActionBar?.title = "와이파이 스캔"
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

    private fun initSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)

        swipeRefreshLayout.setOnRefreshListener {
            if (!wifiManager.startScan()) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initWifiScan() {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        viewModel = ViewModelProvider(this).get(WifiScanViewModel::class.java)
        viewModel.wifiScanResult.value = wifiManager.scanResults.filter { it.SSID.isNotEmpty() }

        wifiScanReceiver = WifiScanReceiver(this, viewModel)
    }

    private fun initRecyclerView() {
        val recyclerViewWifiScanResult: RecyclerView = findViewById(R.id.recyclerViewWifiScanResult)
        val mAdapter = adaptRecycler(recyclerViewWifiScanResult)

        viewModel.wifiScanResult.observe(this) {
            swipeRefreshLayout.isRefreshing = false

            mAdapter.submitList(it)
        }
        viewModel.createWifiEntry.observe(this) {
            if (it != null) {
                val dialog = DeviceRegisterDialog(it)
                dialog.show(this.supportFragmentManager, dialog.tag)
            }
        }
    }
    private fun adaptRecycler(recyclerView: RecyclerView): WifiScanListAdapter {
        val mAdapter = WifiScanListAdapter(viewModel)
        val lm = LinearLayoutManager(this)

        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }
        return mAdapter
    }

    class WifiScanReceiver(
        activity: AppCompatActivity,
        private val viewModel: WifiScanViewModel
    ) {
        private val context: Context = activity
        private val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        private val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                viewModel.wifiScanResult.value =
                    wifiManager.scanResults.filter { it.SSID.isNotEmpty() }
            }
        }

        init {
            val intentFilter = IntentFilter()
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            context.registerReceiver(wifiScanReceiver, intentFilter)
        }
    }
}