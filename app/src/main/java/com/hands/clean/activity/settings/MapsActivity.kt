package com.hands.clean.activity.settings

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.hands.clean.R
import com.hands.clean.dialog.GpsRegisterButtonDialog
import com.hands.clean.dialog.GpsRegisterDialog
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.concurrent.thread


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapsViewModel: MapsViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mMap: GoogleMap
    private lateinit var mMapController: MapController
    private lateinit var mMapListener: MapListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initActionBar()

        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
        .addOnSuccessListener { location : Location ->
            onCallBack(location)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
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

    private fun onCallBack(location: Location) {
        Log.e("current location", location.toString())
        val currentLocation = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMapController = MapController(googleMap)
        mMapListener = MapListener(this, mapsViewModel, googleMap, mMapController)

    }

    class MapListener(
        private val activity: AppCompatActivity,
        private val mapsViewModel: MapsViewModel,
        private val mMap: GoogleMap,
        private val mMapController: MapController
        ) {
        private var selectPositionMarker: Marker? = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(0.0,0.0))
            )
        private var selectPositionCircle: Circle? =
            mMapController.addCircle(LatLng(0.0,0.0), 0.0)

        init {
            selectPositionCircle?.isVisible = false
            selectPositionMarker?.isVisible = false

            valueChangedObserve()

            onClickMap()
        }

        private fun valueChangedObserve() {
            mapsViewModel.selectPosition.observe(activity) { position ->
                selectPositionMarker?.position = position
                selectPositionCircle?.center = position
            }
            mapsViewModel.selectRadius.observe(activity) { radius ->
                selectPositionCircle?.radius = radius
            }
            mapsViewModel.createGpsEntry.observe(activity) { entry ->
                if (entry != null) {
                    mMapController.insertGpsEntry(entry)
                }
            }
        }

        private fun onClickMap() {
            mMap.setOnMapLongClickListener {
                mapsViewModel.selectPosition.value = it
                selectPositionMarker?.isVisible = true
                selectPositionCircle?.isVisible = true

                val gpsRegisterButtonDialog = GpsRegisterButtonDialog(mapsViewModel)
                gpsRegisterButtonDialog.setOnCancelListener {
                    selectPositionCircle?.isVisible = false
                    selectPositionMarker?.isVisible = false
                }

                gpsRegisterButtonDialog.show(activity.supportFragmentManager, gpsRegisterButtonDialog.tag)
            }


            mMap.setOnInfoWindowLongClickListener {
                mMapController.removeGpsEntryByMarker(it)
            }
        }
    }

    class MapController(
        private val mMap: GoogleMap,
    ) {
        private val gpsEntryList: MutableList<GpsEntry> = mutableListOf()

        init {
            getGpsEntries().map {
                addGpsEntry(it)
            }
        }

        private fun getGpsEntries(): List<GpsEntry> {
            return DB.getInstance().gpsDao().getAll()
        }

        fun insertGpsEntry(gpsEntry: GpsEntry) {
            DB.getInstance().gpsDao().insertAll(gpsEntry)
            WashGeofencing.getInstance().initGeofence()
            addGpsEntry(gpsEntry)
        }

        fun removeGpsEntryByMarker(marker: Marker) {
            try {
                val gpsEntry = gpsEntryList.filter {
                    it.marker == marker
                }[0]
                removeGpsEntry(gpsEntry)
            } catch (e: Exception) {

            }
        }

        fun removeGpsEntry(gpsEntry: GpsEntry) {
            DB.getInstance().gpsDao().deleteByRequestId(gpsEntry.requestId)
            gpsEntry.circle?.remove()
            gpsEntry.marker?.remove()
            gpsEntryList.remove(gpsEntry)
        }

        private fun addGpsEntry(gpsEntry: GpsEntry) {
            gpsEntryList.add(gpsEntry)

            val latLng = LatLng(gpsEntry.latitude, gpsEntry.longitude)

            gpsEntry.marker = addMarkers(latLng, gpsEntry.name)
            gpsEntry.circle = addCircle(latLng, gpsEntry.radius.toDouble())
        }

        private fun addMarkers(position: LatLng, title:String): Marker? {
            return mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(title)
            )
        }
        fun addCircle(center: LatLng, radius: Double): Circle? {
            val circleOption = CircleOptions().apply {
                radius(radius)
                center(center)
                strokeColor(Color.BLACK)
                fillColor(0x30ff0000)
                strokeWidth(2f)
            }

            return mMap.addCircle(circleOption)
        }
    }
}