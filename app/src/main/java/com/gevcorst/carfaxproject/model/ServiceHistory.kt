package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServiceHistory(
    val history: List<HistoryX> = listOf(),
    val icon: String = "",
    val iconUrl: String = "",
    val number: Int = 0,
    val text: String = ""
)