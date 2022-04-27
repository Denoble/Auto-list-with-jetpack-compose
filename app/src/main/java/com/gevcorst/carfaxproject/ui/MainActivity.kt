package com.gevcorst.carfaxproject.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.gevcorst.carfaxproject.R
import com.gevcorst.carfaxproject.viewmodel.CarListViewModel
import dagger.hilt.android.AndroidEntryPoint
import values.CarFaxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarFaxTheme{
                val context = LocalContext.current
                val carListViewModel: CarListViewModel by viewModels()
                carListViewModel.updateList(context = context)
                val lists = carListViewModel.carListings.observeAsState(emptyList())
                window.navigationBarColor =
                    resources.getColor(R.color.purple_700, resources.newTheme())
                AppNavigation(carListViewModel)
                //Log.d(javaClass.simpleName, lists.value.toString())
            }
        }
    }
}