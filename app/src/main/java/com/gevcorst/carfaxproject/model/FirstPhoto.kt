package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class FirstPhoto(
    val large: String = "",
    val medium: String = "",
    val small: String = ""
):Parcelable