package org.yankauskas.here.data.net

import org.yankauskas.here.data.entity.CategoriesResponse
import org.yankauskas.here.data.entity.GeocodeResponse
import org.yankauskas.here.data.entity.PlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
interface HereApiService {
    @GET("https://reverse.geocoder.ls.hereapi.com/6.2/reversegeocode.json")
    suspend fun getGeocode(@Query("maxresults") maxResults: Int = 1,
                           @Query("mode") mode: String = "retrieveAddresses",
                           @Query("prox") location: String): Response<GeocodeResponse>

    @GET("https://places.ls.hereapi.com/places/v1/discover/explore")
    suspend fun getPlaces(@Query("at") location: String,
                                 @Query("cat") category: String?): Response<PlacesResponse>

    @GET("https://places.ls.hereapi.com/places/v1/categories/places")
    suspend fun getCategories(@Query("at") location: String): Response<CategoriesResponse>
}