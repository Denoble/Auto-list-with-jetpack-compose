package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRequest(
    val make: String = "",
    val mileageRange: MileageRangeX = MileageRangeX(),
    val model: String = "",
    val priceRange: PriceRange = PriceRange(),
    val radius: Int = 0,
    val srpUrl: String = "",
    val webHost: String = "",
    val yearRange: YearRangeX = YearRangeX(),
    val zip: String = ""
)