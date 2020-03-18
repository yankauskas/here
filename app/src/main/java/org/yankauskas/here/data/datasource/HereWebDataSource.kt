package org.yankauskas.here.data.datasource

import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.data.net.HereApiService
import org.yankauskas.here.presentation.util.nullIfEmpty
import org.yankauskas.here.presentation.util.toHereParam

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class HereWebDataSource(private val api: HereApiService) : BaseDataSource(), HereDataSource {
    override suspend fun getGeocode(location: LatLng) =
        getResource { api.getGeocode(location = location.toHereParam()) }

    override suspend fun getPlaces(
        location: LatLng,
        categories: Set<String>
    ) = getResource {
        api.getPlaces(
            location.toHereParam(),
            categories.joinToString(",").nullIfEmpty()
        )
    }

    override suspend fun getCategories(location: LatLng) =
        getResource { api.getCategories(location.toHereParam()) }

}