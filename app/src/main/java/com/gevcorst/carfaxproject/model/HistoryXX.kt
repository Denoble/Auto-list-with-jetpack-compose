package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryXX(
    val averageMilesPerYear: Int = 0,
    val ownerNumber: Int = 0,
    val useType: String = ""
)