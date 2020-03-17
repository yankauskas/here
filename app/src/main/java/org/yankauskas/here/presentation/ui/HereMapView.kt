package org.yankauskas.here.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.view_map.view.*
import org.yankauskas.here.R

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class HereMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr), LifecycleObserver, OnMapReadyCallback {

    var map: GoogleMap? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_map, this, true)
    }
    override fun onMapReady(map: GoogleMap) {
        this.map = map
        with(map) {
            uiSettings.setAllGesturesEnabled(true)
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