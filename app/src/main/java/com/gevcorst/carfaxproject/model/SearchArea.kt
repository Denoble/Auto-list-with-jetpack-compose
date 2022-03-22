package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchArea(
    val city: String = "",
    val dynamicRadii: List<Int> = listOf(),
    val dynamicRadius: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val radius: Int = 0,
    val state: String = "",
    val zip: String = ""
)