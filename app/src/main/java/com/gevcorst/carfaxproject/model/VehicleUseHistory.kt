package com.gevcorst.carfaxproject.model


import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehicleUseHistory(
    @Embedded
    val history: List<HistoryXX> = listOf(),
    val icon: String = "",
    val iconUrl: String = "",
    val text: String = ""
)