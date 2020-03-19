package org.yankauskas.here.presentation

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.yankauskas.here.data.HereRepository
import org.yankauskas.here.data.entity.CategoryEntity
import org.yankauskas.here.data.net.Resource
import org.yankauskas.here.data.net.RetrofitException
import org.yankauskas.here.data.net.doOnSuccess
import org.yankauskas.here.data.net.mapSuccess
import org.yankauskas.here.presentation.entity.Category
import org.yankauskas.here.presentation.entity.Place
import org.yankauskas.here.presentation.entity.mapper.CategoryMapper
import org.yankauskas.here.presentation.entity.mapper.PlaceMapper
import org.yankauskas.here.presentation.manager.LocationManager
import org.yankauskas.here.presentation.util.HereConst
import org.yankauskas.here.presentation.util.SingleLiveEvent

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class MainViewModel(
    private val locationManager: LocationManager,
    private val hereRepository: HereRepository,
    private val categoryMapper: CategoryMapper,
    private val placeMapper: PlaceMapper
) : ViewModel() {
    val requestLocationEvent by lazy {
        SingleLiveEvent(null)
    }
    val openMap = SingleLiveEvent<Unit>()
    val selectMarker = SingleLiveEvent<LatLng>()
    val location = MutableLiveData<LatLng>()
    val geocode = MutableLiveData<Resource<String>>()
    val getCategories = MutableLiveData<Resource<Unit>>()
    val getPlaces = MutableLiveData<Resource<ArrayList<Place>>>()

    val selectedCategoriesIds = mutableSetOf<String>()
    val categories = arrayListOf<Category>()

    private val initLocationListener: (Location) -> Unit = {
        if (it != HereConst.Location.EMPTY) {
            LatLng(it.latitude, it.longitude).let { latlng ->
                location.value = latlng
                loadGeoCode(latlng)
                loadCategories(latlng)
            }
        }
    }

    private val placesLocationListener: (Location) -> Unit = {
        if (it != HereConst.Location.EMPTY) {
            LatLng(it.latitude, it.longitude).let { latlng ->
                location.value = latlng
                loadGeoCode(latlng)
                loadPlaces(latlng)
            }
        } else getPlaces.value = Resource.Error(
            RetrofitException.UnexpectedRetrofitException(
                Throwable("Location is unavailable")
            )
        )
    }

    override fun onCleared() {
        locationManager.dispose(initLocationListener)
        locationManager.dispose(placesLocationListener)
    }

    fun requestLocation() {
        locationManager.getCurrentLocation(initLocationListener)
    }

    fun loadPlaces() {
        if (selectedCategoriesIds.isEmpty()) {
            getPlaces.value = Resource.Success(arrayListOf())
        } else {
            getPlaces.value = Resource.Loading()
            locationManager.getCurrentLocation(placesLocationListener)
        }
    }

    fun openMap() = openMap.call()

    fun selectPlace(place: Place) {
        selectMarker.value = place.position
    }

    private fun loadPlaces(location: LatLng) = viewModelScope.launch(Dispatchers.IO) {
        getPlaces.postValue(Resource.Loading())
        getPlaces.postValue(hereRepository.getPlaces(location, selectedCategoriesIds)
            .mapSuccess { placeMapper.transform(it) })
    }

    private fun loadGeoCode(location: LatLng) = viewModelScope.launch(Dispatchers.IO) {
        geocode.postValue(Resource.Loading())
        geocode.postValue(hereRepository.getGeocode(location))
    }

    private fun loadCategories(location: LatLng) = viewModelScope.launch(Dispatchers.IO) {
        getCategories.postValue(Resource.Loading())
        getCategories.postValue(
            hereRepository.getCategories(location).mapSuccess { categoryMapper.transform(it) }
                .doOnSuccess {
                    categories.clear()
                    categories.addAll(it)
                }.mapSuccess { Unit })
    }
}