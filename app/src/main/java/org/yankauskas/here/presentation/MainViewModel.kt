package org.yankauskas.here.presentation

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.presentation.manager.LocationManager
import org.yankauskas.here.presentation.util.HereConst
import org.yankauskas.here.presentation.util.SingleLiveEvent

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class MainViewModel(private val locationManager: LocationManager) : ViewModel() {
    val requestLocationEvent by lazy {
        SingleLiveEvent(null)
    }
    val location = SingleLiveEvent<LatLng>()

    private val locationListener: (Location) -> Unit = {
        if (it != HereConst.Location.EMPTY) {
            location.value = LatLng(it.latitude, it.longitude)
        }
    }

    fun requestLocation() {
        locationManager.getCurrentLocation(locationListener)
    }

    override fun onCleared() {
        locationManager.dispose(locationListener)
    }
}