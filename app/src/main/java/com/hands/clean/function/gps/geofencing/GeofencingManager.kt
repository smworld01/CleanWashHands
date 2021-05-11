package com.hands.clean.function.gps.geofencing

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.hands.clean.function.permission.PermissionChecker
import com.hands.clean.function.receiver.GeofenceBroadcastReceiver
import com.hands.clean.function.room.DB
import kotlin.concurrent.thread

object WashGeofencing {
    private lateinit var geofencing: GeofencingManager

    fun init(context: Context) {
        geofencing = GeofencingManager(context)
    }

    fun getInstance() = geofencing
}

class GeofencingManager(context: Context) {
    private val permissionChecker = PermissionChecker(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))

    private var geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun initGeofence() {
        thread {
            if (permissionChecker.isGranted()) {
                val geoFenceList = getGeoFencesInDB()
                add(geoFenceList)
            }
        }
    }

    fun onStop() {
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                Log.e("geofencing", "stop success")
            }
            addOnFailureListener {
                Log.e("geofencing", "stop fail")
            }
        }
    }

    private fun getGeoFencesInDB(): List<Geofence> {
        var locationList = DB.getInstance().gpsDao().getAll()

        locationList = locationList.filter { it.isNotification }

        return locationList.map { entry ->
            getGeofence(Pair(entry.latitude, entry.longitude), entry.radius)
        }
    }

    @SuppressLint("MissingPermission")
    private fun add(geofenceList: List<Geofence>) {
        if (geofenceList.isNotEmpty()) {
            geofencingClient.addGeofences(getGeofencingRequest(geofenceList), geofencePendingIntent).run {
                addOnSuccessListener {
                    Log.e("geofencing", "init success")
                }
                addOnFailureListener {
                    Log.e("geofencing", "init fail")
                }
            }
        }
    }

    private fun getGeofence(geo: Pair<Double, Double>, radius: Float = 50f): Geofence {
        return Geofence.Builder()
            .setRequestId(geo.hashCode().toString())
            .setCircularRegion(geo.first, geo.second, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
    }

    private fun getGeofencingRequest(geofenceList: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }
}