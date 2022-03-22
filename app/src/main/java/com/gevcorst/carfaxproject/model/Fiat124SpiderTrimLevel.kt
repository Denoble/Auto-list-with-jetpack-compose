package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fiat124SpiderTrimLevel(
    val count: Int = 0,
    val text: String = "",
    val url: String = ""
)