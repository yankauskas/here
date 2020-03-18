package org.yankauskas.here.presentation.manager

import android.location.Location

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
interface LocationManager {
    fun getCurrentLocation(listener: (Location) -> Unit)
    fun dispose(listener: (Location) -> Unit)
}