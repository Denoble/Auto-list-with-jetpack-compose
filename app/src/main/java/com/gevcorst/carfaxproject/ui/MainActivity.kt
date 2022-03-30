package com.gevcorst.carfaxproject.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gevcorst.carfaxproject.R
import com.gevcorst.carfaxproject.viewmodel.CarListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            // A surface container using the 'background' color from the theme
            val carListViewModel: CarListViewModel by viewModels()
            carListViewModel.updateList(context = context)
            val lists = carListViewModel.carListings.observeAsState(emptyList())
            val error = carListViewModel.errorMessage.observeAsState("")
            window.navigationBarColor =
                resources.getColor(R.color.purple_700, resources.newTheme())
            MaterialTheme {
                Surface(color = MaterialTheme.colors.primaryVariant) {
                    Home(carListViewModel)
                    Log.d(javaClass.simpleName, lists.value.toString())
                }
            }
        }
    }
}
