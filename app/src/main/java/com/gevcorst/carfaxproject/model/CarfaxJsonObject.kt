package com.gevcorst.carfaxproject.model


import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarfaxJsonObject(
    val backfillCount: Int = 0,
    val breadCrumbs: List<BreadCrumb> = listOf(),
    val dealerNewCount: Int = 0,
    val dealerUsedCount: Int = 0,
    val enhancedCount: Int = 0,
    val facetCountMap: FacetCountMap = FacetCountMap(),
    val listings: List<Listings> = listOf(),
    val page: Int = 0,
    val pageSize: Int = 0,
    val relatedLinks: RelatedLinks = RelatedLinks(),
    val searchArea: SearchArea = SearchArea(),
    val searchRequest: SearchRequest = SearchRequest(),
    val seoUrl: String = "",
    val totalListingCount: Int = 0,
    val totalPageCount: Int = 0
)