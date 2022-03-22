package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AccidentHistory(
    val accidentSummary: List<String> = listOf(),
    val icon: String = "",
    val iconUrl: String = "",
    val text: String = ""
):Parcelable