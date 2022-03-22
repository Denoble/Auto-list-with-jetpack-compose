package com.gevcorst.carfaxproject.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gevcorst.carfaxproject.model.CarfaxJsonObject
import com.gevcorst.carfaxproject.model.Listings
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface CarObjectDAO{
    @Query("SELECT * FROM carlistings ORDER BY year")
    fun getCarListings(): List<Listings>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(carlistings: List<Listings>)
}