package com.hands.clean.activity.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hands.clean.R
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground

class GpsSettingActivity : AppCompatActivity(){
    private val permissionRequester = GpsPermissionRequesterWithBackground(this)
    private val gpsSetting: SystemSettingsGpsManager = SystemSettingsGpsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}