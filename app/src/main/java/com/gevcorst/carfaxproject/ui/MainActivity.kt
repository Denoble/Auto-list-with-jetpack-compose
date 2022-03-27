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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var boxVisible by remember { mutableStateOf(true) }
    val onClick = { newState: Boolean ->
        boxVisible = newState
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Crossfade(targetState = boxVisible, animationSpec = tween(5000)) { visible ->
                when (visible) {
                    true -> CustomButton(
                        text = "Hide", targetState = false,
                        onClick = onClick, bgColor = Color.Black
                    )
                    false -> CustomButton(
                        text = "Show", targetState = true,
                        onClick = onClick, bgColor = Color.Magenta
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(
            visible = boxVisible,
            enter = fadeIn(
                animationSpec = repeatable(
                    10, animation = tween(durationMillis = 2000),
                    repeatMode = RepeatMode.Reverse
                )
            ),
            exit = fadeOut(animationSpec = tween(durationMillis = 5500))
        ) {
            Box(
                modifier = Modifier
                    .animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = tween(durationMillis = 5500)
                        ),
                        exit = slideOutVertically(
                            animationSpec = tween(durationMillis = 5500)
                        )
                    )

                    .size(height = 20.dp, width = 20.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun DrawableRotation() {
    val image = animatedVectorResource(id = R.drawable.loading_animation)
    val atEnd by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = image.painterFor(atEnd = atEnd.not()),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .rotate(360f)
        )
    }
}

@Preview
@Composable
fun DrawableRotationDemo() {
    DrawableRotation()
}

@Preview
@Composable
fun MainScreenDemo() {
    MainScreen()
}

@Composable
fun CustomButton(
    text: String, targetState: Boolean,
    onClick: (Boolean) -> Unit, bgColor: Color = Color.Blue
) {

    Button(
        modifier = Modifier.wrapContentSize(),
        onClick = { onClick(targetState) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = bgColor,
            contentColor = Color.White
        )
    ) {
        Text(
            text, modifier = Modifier.wrapContentSize(),
            style = TextStyle(fontSize = 4.sp)
        )
    }
}