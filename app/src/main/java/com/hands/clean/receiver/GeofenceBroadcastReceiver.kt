package com.hands.clean.receiver

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.hands.clean.function.notification.notify.location.GpsNotify

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.e("geofence", "event start")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition
        geofencingEvent.triggeringGeofences

        // Test that the reported transition was of interest.
        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Log.e("test", "event GEOFENCE_TRANSITION_ENTER")
                GpsNotify(context, geofencingEvent.triggeringGeofences).onNotify()
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Log.e("test", "event GEOFENCE_TRANSITION_EXIT")
            }
            else -> {
                Log.e("test", "event other")

            }
        }
    }

}