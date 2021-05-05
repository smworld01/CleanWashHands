package com.hands.clean.setting

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hands.clean.R
import com.hands.clean.function.gps.GpsSetting
import com.hands.clean.function.gps.GpsTracker
import com.hands.clean.function.permission.GpsPermission
import java.io.IOException
import java.util.*


class GpsSettingActivity : AppCompatActivity(), OnMapReadyCallback {

    private var gpsTracker: GpsTracker = GpsTracker(this)
    private val gpsSetting: GpsSetting = GpsSetting(this)
    private val gpsPermission: GpsPermission = GpsPermission(this)
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps_setting)

        initActionBar()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        if (gpsPermission.isDenied()) {
            gpsPermission.request()
        }

        if (gpsSetting.isDisabled()) {
            gpsSetting.request()
        }
        gpsPermission.registerGrantedCallBack { onCallBack() }
        gpsSetting.registerEnableCallBack { onCallBack() }
    }

    private fun onCallBack() {
        Log.e("test", gpsPermission.isGranted().toString())
        if (gpsSetting.isEnabled() && gpsPermission.isGranted()) {
            gpsTracker.getLocation()
            val latitude: Double = gpsTracker.getLatitude()
            val longitude: Double = gpsTracker.getLongitude()
            Log.e("test", gpsPermission.isGranted().toString())
            val currentLocation = LatLng(latitude, longitude)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        onCallBack()

//        val SEOUL = LatLng(37.56, 126.97)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(SEOUL)
//        markerOptions.title("서울")
//        markerOptions.snippet("한국의 수도")
//        googleMap.addMarker(markerOptions)
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10f))
    }
}