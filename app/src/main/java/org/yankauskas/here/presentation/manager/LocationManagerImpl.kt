package org.yankauskas.here.presentation.manager

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class LocationManagerImpl constructor(private val context: Context) : LocationManager {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest()
    private var currentTask: Task<Void>? = null
    private var listeners = HashSet<(Location) -> Unit>()

    init {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.numUpdates = 1
        locationRequest.maxWaitTime = 5000
    }

    override fun getCurrentLocation(listener: (Location) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            listener(Location("dummyprovider"))
        else {
            listeners.add(listener)
            if (currentTask == null || currentTask?.isComplete == true) {
                currentTask = fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        if (locationResult.locations.size > 0) listeners.forEach { it(locationResult.locations[0]) }
                        else listeners.forEach { it(Location("dummyprovider")) }
                        listeners.clear()
                    }
                }, null)
            }
        }
    }

    override fun dispose(listener: (Location) -> Unit) {
        listeners.remove(listener)
    }
}