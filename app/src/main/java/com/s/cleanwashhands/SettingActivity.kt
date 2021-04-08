package com.s.cleanwashhands

import kotlinx.android.synthetic.main.activity_setting.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.s.cleanwashhands.setting.BluetoothSettingActivity
import com.s.cleanwashhands.setting.GpsSettingActivity
import com.s.cleanwashhands.setting.WifiSettingActivity

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initActionBar()
        initLayout()

    }
    private fun initLayout() {
         buttonBlueTooth.setOnClickListener {
             startActivity(Intent(this, BluetoothSettingActivity::class.java))
         }

         buttonWifi.setOnClickListener {
             startActivity(Intent(this, WifiSettingActivity::class.java))
         }

         buttonGPS.setOnClickListener {
             startActivity(Intent(this, GpsSettingActivity::class.java))
         }

         buttonTutorial.setOnClickListener {
             startActivity(Intent(this, TutorialActivity::class.java))
         }
    }
    private fun initActionBar() {
        supportActionBar?.title = "설정"
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