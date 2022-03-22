package com.gevcorst.carfaxproject.repository

import android.util.Log
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.repository.database.CarObjectDAO
import com.gevcorst.carfaxproject.repository.network.CarFaxJsonObjectAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class CarListRepository private constructor(
    private val carObjectDao: CarObjectDAO,
    private val carApiService: CarFaxJsonObjectAPIService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Fetch a new list of carListings from the network, and append them to [carObjectDAO]
     */
    suspend fun fetchCarListings() {
        val carFaxJsonObject = carApiService.getCarFaxJsonObjets()
        Log.d("CarListRepository", ": ${carFaxJsonObject.listings}")
        carObjectDao.insertAll(carlistings = carFaxJsonObject.listings)
    }

    val carListings: List<Listings>
        get() = carObjectDao.getCarListings()

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: CarListRepository? = null

        fun getInstance(carObjectDao: CarObjectDAO, carApiService: CarFaxJsonObjectAPIService) =
            instance ?: synchronized(this) {
                instance ?: CarListRepository(
                    carObjectDao = carObjectDao,
                    carApiService = carApiService
                ).also { instance = it }
            }
    }

}