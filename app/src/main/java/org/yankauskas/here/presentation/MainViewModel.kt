package org.yankauskas.here.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.yankauskas.here.presentation.manager.LocationManager

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class MainViewModel(private val locationManager: LocationManager) : ViewModel() {
    val location = MutableLiveData<String>()

    fun requestLocation() {
        locationManager.getCurrentLocation { location.value = it.toString()}
    }
}