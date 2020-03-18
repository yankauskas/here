package org.yankauskas.here.data.datasource

import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.data.entity.CategoriesResponse
import org.yankauskas.here.data.entity.GeocodeResponse
import org.yankauskas.here.data.entity.PlacesResponse
import org.yankauskas.here.data.net.Resource

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
interface HereDataSource {
    suspend fun getGeocode(location: LatLng): Resource<GeocodeResponse>
    suspend fun getPlaces(location: LatLng, categories: Set<String>): Resource<PlacesResponse>
    suspend fun getCategories(location: LatLng): Resource<CategoriesResponse>
}