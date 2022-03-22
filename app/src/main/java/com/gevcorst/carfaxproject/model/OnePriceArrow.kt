package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class OnePriceArrow(
    val arrow: String = "",
    val arrowUrl: String = "",
    val icon: String = "",
    val iconUrl: String = "",
    val order: Int = 0,
    val text: String = ""
):Parcelable