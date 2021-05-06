package com.hands.clean.activity.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.hands.clean.R
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.gps.GpsTracker
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground


class GpsSettingActivity : AppCompatActivity(), OnMapReadyCallback {

    private var gpsTracker: GpsTracker = GpsTracker(this)
    private val gpsSetting: SystemSettingsGpsManager = SystemSettingsGpsManager(this)
    private val permissionRequester = GpsPermissionRequesterWithBackground(this)
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps_setting)

        initActionBar()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        permissionRequester.registerGranted { gpsSetting.onRequest() }
        permissionRequester.registerDenied { finish() }
        permissionRequester.onRequest()
        gpsSetting.registerEnableCallBack { onCallBack() }
    }

    private fun onCallBack() {
        if (gpsSetting.isEnabled() && permissionRequester.isGranted()) {
            gpsTracker.getLocation()
            val latitude: Double = gpsTracker.getLatitude()
            val longitude: Double = gpsTracker.getLongitude()
            val currentLocation = LatLng(latitude, longitude)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18f))
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