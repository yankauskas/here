package org.yankauskas.here.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.view_map.view.*
import org.yankauskas.here.R
import org.yankauskas.here.presentation.entity.Place

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class HereMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), LifecycleObserver, OnMapReadyCallback {

    var map: GoogleMap? = null
    var onMapReadyListener: () -> Unit = {}
    var markers = arrayListOf<Marker>()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_map, this, true)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        with(map) {
            uiSettings.setAllGesturesEnabled(true)
        }
        onMapReadyListener()
    }

    fun showCurrentLocation(location: LatLng) {
        map?.let {
            it.isMyLocationEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = true
            it.uiSettings.isZoomControlsEnabled = true
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
        }
    }

    fun renderMarkers(places: ArrayList<Place>) {
        map?.let { map ->
            markers.forEach { it.remove() }
            markers.clear()
            markers.addAll(places.map {
                map.addMarker(MarkerOptions().position(it.position).title(it.title))
            })
            var bounds = LatLngBounds.Builder().include(markers.first().position).build()
            markers.forEach { bounds = bounds.including(it.position) }
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30))
        }
    }

    fun selectMarker(location: LatLng) {
        markers.firstOrNull { it.position == location }?.let {
            it.showInfoWindow()
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() = mapView.getMapAsync(this)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = mapView.onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() = mapView.onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() = mapView.onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() = mapView.onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() = mapView.onDestroy()
}