package org.yankauskas.here.data

import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.data.datasource.HereWebDataSource
import org.yankauskas.here.data.net.mapSuccess

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class HereRepository(private val dataSource: HereWebDataSource) {
    suspend fun getGeocode(location: LatLng) = dataSource.getGeocode(location)
        .mapSuccess { it.response.view.first().result.first().location.address.label }

    suspend fun getPlaces(
        location: LatLng,
        categories: Set<String>
    ) = dataSource.getPlaces(location, categories).mapSuccess { it.results.items }

    suspend fun getCategories(location: LatLng) =
        dataSource.getCategories(location).mapSuccess { it.items }
}