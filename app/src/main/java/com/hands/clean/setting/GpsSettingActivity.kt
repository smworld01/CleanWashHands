package com.hands.clean.setting

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hands.clean.R
import com.hands.clean.function.gps.GpsSetting
import com.hands.clean.function.gps.GpsTracker
import com.hands.clean.function.permission.GpsPermission
import java.io.IOException
import java.util.*


class GpsSettingActivity : AppCompatActivity() {

    private var gpsTracker: GpsTracker? = null
    private val gpsSetting: GpsSetting = GpsSetting(this)
    private val gpsPermission: GpsPermission = GpsPermission(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps_setting)

        initActionBar()
        setContentView(R.layout.activity_gps_setting)


        if (gpsPermission.isDenied()) {
            gpsPermission.request()
        }

        if (gpsSetting.isDisabled()) {
            gpsSetting.request()
        }

        val textview_address = findViewById<View>(R.id.textview) as TextView


        val ShowLocationButton: Button = findViewById<View>(R.id.button) as Button
        ShowLocationButton.setOnClickListener {
            gpsTracker = GpsTracker(this@GpsSettingActivity)
            val latitude: Double = gpsTracker!!.getLatitude()
            val longitude: Double = gpsTracker!!.getLongitude()
            Toast.makeText(
                this@GpsSettingActivity,
                "현재위치 \n위도 $latitude\n경도 $longitude",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun initActionBar() {
        supportActionBar?.title = "GPS 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
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
}