package org.yankauskas.here.presentation.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.view_map.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.yankauskas.here.R
import org.yankauskas.here.presentation.MainViewModel
import org.yankauskas.here.presentation.util.observeLiveData
import org.yankauskas.here.presentation.util.observeResource


class NearbyMapFragment : Fragment(R.layout.fragment_map) {
    private val myViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hereMapView.mapView.onCreate(arguments)
        lifecycle.addObserver(hereMapView)

        hereMapView.onMapReadyListener = {
            observeLiveData(myViewModel.location) {
                hereMapView.showCurrentLocation(it)
            }
            observeResource(myViewModel.getPlaces) {
                hereMapView.renderMarkers(it)
            }
            observeLiveData(myViewModel.selectMarker) {
                hereMapView.selectMarker(it)
            }
        }
    }
}