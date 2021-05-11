package com.hands.clean.activity.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import com.hands.clean.R
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground

class GpsSettingActivity : AppCompatActivity(){
    private val permissionRequester = GpsPermissionRequesterWithBackground(this)
    private val gpsSetting: SystemSettingsGpsManager = SystemSettingsGpsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()

        permissionRequester.registerGranted { gpsSetting.onRequest() }
        permissionRequester.registerDenied { finish() }
        permissionRequester.onRequest()

        setContentView(R.layout.activity_gps_setting)
        val imageButton: Button = findViewById<View>(R.id.buttonRegisterGPS) as Button
        imageButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)

        })
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