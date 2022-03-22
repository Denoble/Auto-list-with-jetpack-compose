package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MileageRangeX(
    val max: Int = 0,
    val min: Int = 0
)