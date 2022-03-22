package com.gevcorst.carfaxproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.repository.CarListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

enum class ServiceStatus { SENDING, LOADING, ERROR, DONE, IDLE }


@HiltViewModel
class CarListViewModel @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(
    private val carListRepository: CarListRepository
) : ViewModel() {
    val scope = MainScope()
    private val _isDataFetched = MutableLiveData<Boolean>(false)
    val isDataFetched: LiveData<Boolean> get() = _isDataFetched
    private val _serviceStatus = MutableLiveData<ServiceStatus>(ServiceStatus.IDLE)
    val serviceStatus = _serviceStatus
    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> = _errorMessage
    private val _carListings = MutableLiveData<List<Listings>>(emptyList())
    val carListings: LiveData<List<Listings>> get() = _carListings
    fun getListInDatabase(): Flow<List<Listings>> {
        return flow {
            val listings = carListRepository.carListings
            emit(listings)
        }.flowOn(Dispatchers.IO)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun updateList(context: Context) {
        val isDatabaseAvailable = doesDatabaseExist(context)
        viewModelScope.launch {
            _serviceStatus.value = ServiceStatus.LOADING
            if (isDatabaseAvailable) {
                getListInDatabase().collect {
                    _carListings.value = it
                    Log.d(javaClass.simpleName + " FROMDATABASE", it.toString())
                    _serviceStatus.value = ServiceStatus.DONE
                }
            } else {
                val listingJob: Deferred<Flow<List<Listings>>> = async(Dispatchers.Default) {
                    getListInDatabase()
                }
                carListRepository.fetchCarListings()
                val listingsValue = listingJob.await()
                listingsValue.collect {
                    _carListings.value = it
                    Log.d(javaClass.simpleName, it.toString())
                    _serviceStatus.value = ServiceStatus.DONE
                }
            }
        }
    }

    private fun doesDatabaseExist(context: Context, dbName: String = "carfaxDatabase.db"): Boolean {
        val dbFile: File = context.getDatabasePath(dbName)
        return dbFile.exists()
    }
}
