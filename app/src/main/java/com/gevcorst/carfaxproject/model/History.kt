package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class History(
    val city: String = "",
    val endOwnershipDate: String = "",
    val ownerNumber: Int = 0,
    val purchaseDate: String = "",
    val state: String = ""
):Parcelable