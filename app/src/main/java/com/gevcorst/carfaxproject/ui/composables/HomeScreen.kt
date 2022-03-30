package com.gevcorst.carfaxproject.ui

import android.widget.TextView
import androidx.compose.animation.core.*
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codelab.theming.ui.start.theme.bluegrey
import com.gevcorst.carfaxproject.R
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.viewmodel.CarListViewModel
import com.gevcorst.carfaxproject.viewmodel.ServiceStatus
import org.w3c.dom.Text

@Composable
fun Home(viewModel: CarListViewModel) {
    val listings = viewModel.carListings.observeAsState(emptyList())
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val serviceStatus = viewModel.serviceStatus.observeAsState(ServiceStatus.IDLE)
    val selectedIndex = remember { mutableStateOf(0) }
    var restApiServiceCallDone by remember { mutableStateOf(false) }

    MaterialTheme() {
        Scaffold(scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBar() },
            content = {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(carListings = listings, serviceStatus = serviceStatus)
                }
            },
            bottomBar = { BottomNavigationBar(selectedIndex = selectedIndex) })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBar() {
    Surface(
        modifier = Modifier.size(100.dp),
        color = Color.White
    ) {
    }
}

@Composable
fun CarDetailsCard(listing: Listings) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, year, make, model, trim, price, mileage, location) = createRefs()
        }
    }

}

@Composable
fun AppNavigation(carListings: State<List<Listings>>, serviceStatus: State<ServiceStatus>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "carlists") {
        composable("carlists") {

            CarItemList(
                listings = carListings,
                serviceStatus = serviceStatus,
                navHostController = navController
            )
        }
        composable("carDetails") {
            CarDetailsCard(listing = carListings.value[0])
        }

    }
    Row(modifier = Modifier.size(130.dp)) {

    }
}

@Composable
fun ProfileContent(listing: Listings, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 48.dp)
            .clickable(onClick = { clickAction.invoke() }),
        elevation = 8.dp,
        shape = Shapes.myshape.medium,
        border = BorderStroke(3.dp, bluegrey),
        backgroundColor = bluegrey
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
        ) {
            val (image, year, make, model, trim, price, mileage, location) = createRefs()
            val url = remember { listing.images.firstPhoto.medium }
            val painter =
                rememberAsyncImagePainter(
                    ImageRequest.Builder //Used while loading
                        (LocalContext.current).data(data = url)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true) //Crossfade animation between images
                            placeholder(R.drawable.loading_animation) //Used while loading
                            fallback(R.drawable.ic_baseline_broken_image_24) //Used if data is null
                            error(R.drawable.ic_baseline_broken_image_24) //Used when loading returns with error
                        }).build()
                )


            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                //Use painter in Image composable
                painter = painter,
                contentDescription = stringResource(id = R.string.imageloader)
            )

            CustomizedText(text = listing.year.toString(), modifier = Modifier.constrainAs(year) {
                top.linkTo(image.top, margin = 8.dp)
                start.linkTo(image.end, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
            CustomizedText(text = listing.make, modifier = Modifier.constrainAs(make) {
                top.linkTo(year.top, margin = 16.dp)
                start.linkTo(year.end, margin = 16.dp)
                baseline.linkTo(year.baseline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
            CustomizedText(text = listing.model, modifier = Modifier.constrainAs(model) {
                top.linkTo(make.top, margin = 16.dp)
                start.linkTo(make.end, margin = 16.dp)
                baseline.linkTo(make.baseline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
           CustomizedText(text = listing.trim, modifier = Modifier.constrainAs(trim) {
                top.linkTo(model.top, margin = 16.dp)
                start.linkTo(model.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 8.dp)
                baseline.linkTo(model.baseline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
            CustomizedText(
                text = stringResource(id = R.string.dollar_sign) + listing.currentPrice.toString(),
                modifier = Modifier.constrainAs(price) {
                    top.linkTo(year.bottom, margin = 16.dp)
                    start.linkTo(year.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                })
            CustomizedText(
                text = listing.currentPrice.toString() + stringResource(id = R.string.mileage_symbol),
                modifier = Modifier.constrainAs(mileage) {
                    top.linkTo(price.top, margin = 16.dp)
                    start.linkTo(price.end, margin = 16.dp)
                    baseline.linkTo(price.baseline)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
            CustomizedText(
                text = listing.dealer.city + " " + listing.dealer.state,
                modifier = Modifier.constrainAs(location) {
                    top.linkTo(price.bottom, margin = 16.dp)
                    start.linkTo(price.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
        }
    }

}


@Composable
fun CarItemList(
    listings: State<List<Listings>>,
    serviceStatus: State<ServiceStatus>,
    navHostController: NavHostController?
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (lazyColum) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColum) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(Color.White)

        ) {
            items(listings.value) { listing ->
                when (serviceStatus.value) {
                    ServiceStatus.IDLE,
                    ServiceStatus.SENDING,
                    ServiceStatus.LOADING -> {
                        RotationDemo()
                    }
                    ServiceStatus.DONE -> {
                        ProfileContent(listing = listing) {
                            navHostController?.navigate("carDetails")

                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

}

@Composable
fun CustomizedText(text:String,modifier: Modifier,textAlign: TextAlign = TextAlign.Start) {
    Text( text = text, modifier = modifier,style = TextStyle(fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Bold, fontSize = 10.sp,))
}

@Composable
fun RotationDemo() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing)

        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.loading_img),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}

@Composable
fun TopAppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = stringResource(id = R.string.top_app_bar_home_icon_descriptions)
            )
        }
    )
}

@Composable
fun BottomNavigationBar(selectedIndex: MutableState<Int>) {
    BottomNavigation(elevation = 10.dp) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home, "")
        },
            label = { Text(text = "Home") },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Favorite, "")
        },
            label = { Text(text = "Favorite") },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person, "")
        },
            label = { Text(text = "Profile") },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
            })
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun LoadingAnimations() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        strokeWidth = 3.dp, color = MaterialTheme.colors.primary
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Preview
@Composable
fun LoadingAnimationsPreview() {
    LoadingAnimations()
}