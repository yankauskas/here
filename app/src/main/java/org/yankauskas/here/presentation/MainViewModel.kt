package org.yankauskas.here.presentation

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.yankauskas.here.data.HereRepository
import org.yankauskas.here.data.net.Resource
import org.yankauskas.here.presentation.manager.LocationManager
import org.yankauskas.here.presentation.util.HereConst
import org.yankauskas.here.presentation.util.SingleLiveEvent

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class MainViewModel(
    private val locationManager: LocationManager,
    private val hereRepository: HereRepository
) : ViewModel() {
    val requestLocationEvent by lazy {
        SingleLiveEvent(null)
    }
    val location = MutableLiveData<LatLng>()
    val geocode = MutableLiveData<Resource<String>>()

    private val locationListener: (Location) -> Unit = {
        if (it != HereConst.Location.EMPTY) {
            LatLng(it.latitude, it.longitude).let { latlng ->
                location.value = latlng
                loadGeoCode(latlng)
            }

        }
    }

    override fun onCleared() {
        locationManager.dispose(locationListener)
    }

    fun requestLocation() {
        locationManager.getCurrentLocation(locationListener)
    }

    fun loadGeoCode(location: LatLng) = viewModelScope.launch(Dispatchers.Main) {
        geocode.value = Resource.Loading()
        geocode.value = withContext(Dispatchers.IO) { hereRepository.getGeocode(location) }
    }
}