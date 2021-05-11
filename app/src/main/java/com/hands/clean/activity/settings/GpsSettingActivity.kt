package com.hands.clean.activity.settings

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.hands.clean.R
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.GpsEntry
import kotlin.concurrent.thread


class GpsSettingActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var gpsSetting: SystemSettingsGpsManager
    private lateinit var permissionRequester: GpsPermissionRequesterWithBackground
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps_setting)

        initActionBar()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        gpsSetting = SystemSettingsGpsManager(this)
        gpsSetting.registerEnableCallBack {
            fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location ->
                onCallBack(location)
            }
        }
        gpsSetting.registerDisableCallBack { finish() }

        permissionRequester = GpsPermissionRequesterWithBackground(this)
        permissionRequester.registerGranted { gpsSetting.onRequest() }
        permissionRequester.registerDenied { finish() }
        permissionRequester.onRequest()

    }

    private fun onCallBack(location: Location) {
        Log.e("current location", location.toString())
        val currentLocation = LatLng(location.latitude, location.longitude)
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18f))
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
        drawCircle()
    }

    private fun drawCircle() {
        lateinit var locationList: List<GpsEntry>
        thread {

            locationList = getLocation()

        }.join()

        locationList.map {
            mMap?.addCircle(convertLocationToCircle(it))
        }
    }

    private fun getLocation(): List<GpsEntry> {
        return DB.getInstance().gpsDao().getAll()
    }

    private fun convertLocationToCircle(gpsEntry: GpsEntry): CircleOptions {
        val circleOption = CircleOptions()

        circleOption.radius(gpsEntry.radius.toDouble())
        circleOption.center(LatLng(gpsEntry.latitude, gpsEntry.longitude))
        circleOption.strokeColor(Color.BLACK)
        circleOption.fillColor(0x30ff0000)
        circleOption.strokeWidth(2f)
        return circleOption
    }
}