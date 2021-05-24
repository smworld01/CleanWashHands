package com.hands.clean.activity.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hands.clean.R
import com.hands.clean.activity.TutorialActivity
import com.hands.clean.activity.settings.*
import com.hands.clean.activity.settings.bluetooth.BluetoothSettingActivity
import com.hands.clean.activity.settings.gps.GpsSettingActivity
import com.hands.clean.activity.settings.wifi.WifiSettingActivity

class SettingsFragment : Fragment() {

    private lateinit var galleryViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        initLayout(root)
        return root
    }
    private fun initLayout(root: View) {
        val buttonBlueTooth: ConstraintLayout = root.findViewById(R.id.buttonBluetooth)
        val buttonWifi: ConstraintLayout = root.findViewById(R.id.buttonWifi)
        val buttonGPS: ConstraintLayout = root.findViewById(R.id.buttonGPS)
        val buttonSwitch: ConstraintLayout = root.findViewById(R.id.buttonSwitch)
        val buttonTutorial: ConstraintLayout = root.findViewById(R.id.buttonTutorial)
        val buttonTest: Button = root.findViewById(R.id.buttonTest)



        buttonBlueTooth.setOnClickListener {
            startActivity(Intent(root.context, BluetoothSettingActivity::class.java))
        }

        buttonWifi.setOnClickListener {
            startActivity(Intent(root.context, WifiSettingActivity::class.java))
        }

        buttonGPS.setOnClickListener {
            startActivity(Intent(root.context, GpsSettingActivity::class.java))
        }

        buttonTutorial.setOnClickListener {
            startActivity(Intent(root.context, TutorialActivity::class.java))
        }

        buttonTest.setOnClickListener {
            startActivity(Intent(root.context, TestActivity::class.java))
        }
    }
}