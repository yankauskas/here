package org.yankauskas.here.presentation.util

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */

fun LatLng.toHereParam() = "$latitude,$longitude"