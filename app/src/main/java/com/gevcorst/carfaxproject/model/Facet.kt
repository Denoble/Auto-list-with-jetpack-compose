package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Facet(
    val encodedName: String = "",
    val name: String = "",
    val value: Int = 0
)