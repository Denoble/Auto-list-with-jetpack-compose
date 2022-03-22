package com.gevcorst.carfaxproject.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedLinks(
    @Json(name = "Fiat 124 Spider Trim Levels")
    val fiat124SpiderTrimLevels: List<Fiat124SpiderTrimLevel> = listOf(),
    @Json(name = "Similar Cars")
    val similarCars: List<SimilarCar> = listOf(),
    @Json(name = "Used Fiat 124 Spider by Year")
    val usedFiat124SpiderByYear: List<UsedFiat124SpiderByYear> = listOf()
)