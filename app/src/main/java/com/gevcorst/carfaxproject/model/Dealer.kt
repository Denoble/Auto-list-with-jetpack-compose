package com.gevcorst.carfaxproject.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Dealer(
    val address: String = "",
    val backfill: Boolean = false,
    val carfaxId: String = "",
    val cfxMicrositeUrl: String = "",
    val city: String = "",
    val dealerAverageRating: Double = 0.0,
    val dealerInventoryUrl: String = "",
    val dealerLeadType: String = "",
    val dealerReviewComments: String = "",
    val dealerReviewCount: Int = 0,
    val dealerReviewDate: String = "",
    val dealerReviewRating: Int = 0,
    val dealerReviewReviewer: String = "",
    val dealerReviewTitle: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val name: String = "",
    val onlineOnly: Boolean = false,
    val phone: String = "",
    val state: String = "",
    val zip: String = ""
):Parcelable