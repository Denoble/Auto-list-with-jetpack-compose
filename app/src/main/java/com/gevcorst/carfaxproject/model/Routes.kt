package com.gevcorst.carfaxproject.model

import com.gevcorst.carfaxproject.viewmodel.ServiceStatus


sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Details : Routes("details")
}
data class ResAPIStatus(val carLists:List<Listings>,val status:ServiceStatus)