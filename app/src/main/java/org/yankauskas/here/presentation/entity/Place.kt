package org.yankauskas.here.presentation.entity

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 19.03.2020.
 */
data class Place(
    val position: LatLng,
    val distance: Long,
    val title: String,
    val icon: String,
    val id: String
)