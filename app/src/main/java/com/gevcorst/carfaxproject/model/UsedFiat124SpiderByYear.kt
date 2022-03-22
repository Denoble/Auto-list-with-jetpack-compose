package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsedFiat124SpiderByYear(
    val count: Int = 0,
    val text: String = "",
    val url: String = ""
)