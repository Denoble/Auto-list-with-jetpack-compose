package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import androidx.room.Embedded
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Images(
    val baseUrl: String = "",
    val firstPhoto: FirstPhoto = FirstPhoto(),
    @Embedded
    val large: List<String> = listOf(),
    @Embedded
    val medium: List<String> = listOf(),
    @Embedded
    val small: List<String> = listOf()
):Parcelable