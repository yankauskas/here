package org.yankauskas.here.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
data class GeocodeResponse(@SerializedName("Response") val response: Response)

data class Response(@SerializedName("View") val view: List<View>)

data class View(@SerializedName("Result") val result: List<Result>)

data class Result(@SerializedName("Location") val location: Location)

data class Location(@SerializedName("Address") val address: Address)

data class Address(@SerializedName("Label") val label: String)