package org.yankauskas.here.data.entity

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */

data class PlacesResponse (
    val results: Results
)

data class Results (
    val items: List<PlaceEntity>
)

data class PlaceEntity (
    val position: List<Double>,
    val distance: Long,
    val title: String,
    val icon: String,
    val id: String
)