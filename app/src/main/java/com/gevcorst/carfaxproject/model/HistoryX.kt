package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryX(
    val city: String = "",
    val date: String = "",
    val description: String = "",
    val odometerReading: Int = 0,
    val source: String = "",
    val state: String = ""
)