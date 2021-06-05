package com.hands.clean.activity.settings.gps

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.hands.clean.R
import com.hands.clean.dialog.GpsInfoDialog
import com.hands.clean.dialog.GpsRegisterButtonDialog
import com.hands.clean.function.adapter.GpsListAdapter
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.TrackerEntry
import com.hands.clean.function.settings.WashSettingsManager


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapsViewModel: MapsViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mMap: GoogleMap
    private lateinit var mMapController: MapController
    private lateinit var mMapListener: MapListener

    private lateinit var drawLayout: DrawerLayout
    private lateinit var navLayout: RelativeLayout
    private val gpsManager = SystemSettingsGpsManager(this)
    private val washSettingsManager = WashSettingsManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initActionBar()

        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        initDrawerView()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun initActionBar() {
        supportActionBar?.title = "지도에서 등록하기"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_menu -> {
                if(drawLayout.isOpen) {
                    drawLayout.closeDrawer(navLayout)
                } else {
                    drawLayout.openDrawer(navLayout)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initDrawerView() {
        drawLayout =  findViewById(R.id.drawer_layout)
        navLayout = findViewById(R.id.nav_view)
        initRecyclerView()
        initRecyclerViewClickEvent()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewGpsList)
        val lm = LinearLayoutManager(this)
        val mAdapter = GpsListAdapter(mapsViewModel)

        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = lm
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.color.black, null)!!)
            addItemDecoration(itemDecoration)
        }

        DB.getInstance().gpsDao().getAllByLiveData().observe(this) {
            mAdapter.submitList(it)
        }
    }

    private fun initRecyclerViewClickEvent() {
        mapsViewModel.clickInRecycler.observe(this) {
            it?.let {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                drawLayout.closeDrawers()
            }
        }
        mapsViewModel.longClickInRecycler.observe(this) {
            it?.let {
                val gpsInfoDialog = GpsInfoDialog(it)
                gpsInfoDialog.show(supportFragmentManager, gpsInfoDialog.tag)
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMapController = MapController(this, googleMap)
        mMapListener = MapListener(this, mapsViewModel, googleMap, mMapController)

        requestLocationService()
        showTutorial()
    }

    private fun requestLocationService() {
        gpsManager.registerEnableCallBack {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18f))
                    } else {
                        Toast.makeText(this, "위치 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        gpsManager.registerDisableCallBack {
            Toast.makeText(this, "위치 서비스가 활성화되지 않았습니다.", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        gpsManager.registerCancelCallback { finish() }
        gpsManager.onRequest()
    }

    private fun showTutorial() {
        if (!washSettingsManager.tutorialMaps) {
            washSettingsManager.tutorialMaps = true
            AlertDialog.Builder(this).apply {
                setTitle("사용 방법")
                setMessage(
                    """
                    지도에서 특정 위치를 길게 누르면 위치를 등록할 수 있습니다.
                """.trimIndent()
                )
                setPositiveButton("확인") { dialog, _ ->
                    dialog.cancel()
                }
                create().show()
            }
        }
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
            onClickMarker()
        }

        private fun valueChangedObserve() {
            mapsViewModel.selectPosition.observe(activity) { position ->
                selectPositionMarker?.position = position
                selectPositionCircle?.center = position
            }
            mapsViewModel.selectRadius.observe(activity) { radius ->
                selectPositionCircle?.radius = radius
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
        }
        private fun onClickMarker() {
            mMap.setOnMarkerClickListener {
                val gpsEntry: GpsEntry = it.tag as GpsEntry
                gpsEntry.circle?.isVisible = true

                return@setOnMarkerClickListener false
            }
            mMap.setOnInfoWindowCloseListener {
                val gpsEntry: GpsEntry = it.tag as GpsEntry
                gpsEntry.circle?.isVisible = false
            }
            mMap.setOnInfoWindowClickListener {
                val gpsEntry = it.tag as GpsEntry
                val gpsInfoDialog = GpsInfoDialog(gpsEntry)
                gpsInfoDialog.show(activity.supportFragmentManager, gpsInfoDialog.tag)
            }
        }
    }

    class MapController(
        private val activity: AppCompatActivity,
        private val mMap: GoogleMap
    ) {
        private val gpsEntryList: MutableList<GpsEntry> = mutableListOf()

        init {
            getGpsEntries().observe(activity) {
                submitList(it)
            }
        }
        private fun submitList(newList: List<GpsEntry>) {
            val diffCallback = TrackerEntry.Companion.DateCountDiffCallback
            val newItems = newList.iterator()
            val oldItems = gpsEntryList.toList().iterator()

            if (!newItems.hasNext()) {
                oldItems.forEach {
                    removeGpsEntry(it)
                }
                return
            } else if (!oldItems.hasNext()) {
                newItems.forEach {
                    addGpsEntry(it)
                }
                return
            }
            var newItem: GpsEntry? = null
            var oldItem: GpsEntry? = null

            while (newItems.hasNext() && oldItems.hasNext()) {
                when {
                    oldItem == newItem -> {
                        newItem = newItems.next()
                        oldItem = oldItems.next()
                    }
                    oldItem!!.uid < newItem!!.uid -> {
                        oldItem = oldItems.next()
                    }
                    oldItem.uid > newItem.uid -> {
                        newItem = newItems.next()
                    }
                }

                if (diffCallback.areItemsTheSame(oldItem!!, newItem!!)) {
                    if (!diffCallback.areContentsTheSame(oldItem, newItem)) {
                        modifyGpsEntry(oldItem, newItem)
                    }
                } else {
                    if (oldItem.uid < newItem.uid) {
                        removeGpsEntry(oldItem)
                    } else {
                        addGpsEntry(newItem)
                    }
                }
            }
            while (newItems.hasNext()) {
                newItem = newItems.next()
                if (newItem.uid == oldItem!!.uid) break
                addGpsEntry(newItem)
            }
            while (oldItems.hasNext()) {
                oldItem = oldItems.next()
                if (newItem!!.uid == oldItem.uid) break
                removeGpsEntry(oldItem)
            }
        }

        private fun getGpsEntries(): LiveData<List<GpsEntry>> {
            return DB.getInstance().gpsDao().getAllByLiveData()
        }

        private fun removeGpsEntry(gpsEntry: GpsEntry) {
            gpsEntry.circle?.remove()
            gpsEntry.marker?.remove()
            gpsEntryList.remove(gpsEntry)
        }

        private fun modifyGpsEntry(oldGpsEntry: GpsEntry, newGpsEntry: GpsEntry) {
            Log.e("modify", "$oldGpsEntry || $newGpsEntry")
            oldGpsEntry.radius = newGpsEntry.radius
            oldGpsEntry.name = newGpsEntry.name
            oldGpsEntry.marker?.let {
                it.title = newGpsEntry.name
            }
            oldGpsEntry.circle?.let {
                it.radius = newGpsEntry.radius.toDouble()
            }
        }

        private fun addGpsEntry(gpsEntry: GpsEntry) {
            gpsEntryList.add(gpsEntry)

            val latLng = LatLng(gpsEntry.latitude, gpsEntry.longitude)

            gpsEntry.marker = addMarkers(latLng, gpsEntry.name)
            gpsEntry.circle = addCircle(latLng, gpsEntry.radius.toDouble())

            gpsEntry.marker?.tag = gpsEntry
            gpsEntry.circle?.tag = gpsEntry
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

            return mMap.addCircle(circleOption).apply {
                isVisible = false
            }
        }
    }
}