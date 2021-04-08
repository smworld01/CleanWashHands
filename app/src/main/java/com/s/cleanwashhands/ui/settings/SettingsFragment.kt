package com.s.cleanwashhands.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.s.cleanwashhands.R
import com.s.cleanwashhands.TutorialActivity
import com.s.cleanwashhands.setting.BluetoothSettingActivity
import com.s.cleanwashhands.setting.GpsSettingActivity
import com.s.cleanwashhands.setting.WifiSettingActivity

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
        var buttonBlueTooth: Button= root.findViewById(R.id.buttonBlueTooth)
        var buttonWifi: Button= root.findViewById(R.id.buttonWifi)
        var buttonGPS: Button= root.findViewById(R.id.buttonGPS)
        var buttonTutorial: Button= root.findViewById(R.id.buttonTutorial)



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
    }
}