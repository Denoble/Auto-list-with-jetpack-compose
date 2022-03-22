package com.gevcorst.carfaxproject.ui

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gevcorst.carfaxproject.R
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.viewmodel.CarListViewModel
import com.gevcorst.carfaxproject.viewmodel.ServiceStatus

@Composable
fun Home(viewModel: CarListViewModel) {
    MaterialTheme() {
        Scaffold(topBar = { AppBar(viewModel) }) {
        }
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
fun ProfileCard(listing: Listings) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileContent(listing = listing, modifier = Modifier.wrapContentSize())
    }
}

@Composable
fun ProfilePicture(carListing: Listings, modifier: Modifier) {
    Row(modifier = Modifier.size(130.dp)) {

    }
}

@Composable
fun ProfileContent(listing: Listings, modifier: Modifier) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            //Use painter in Image composable
            painter = painter,
            contentDescription = stringResource(id = R.string.imageloader)
        )
        Text(text = listing.year.toString(), modifier = Modifier.constrainAs(year) {
            top.linkTo(image.top, margin = 8.dp)
            start.linkTo(image.end, margin = 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }, textAlign = TextAlign.Start)
        Text(text = listing.make, modifier = Modifier.constrainAs(make) {
            top.linkTo(year.top, margin = 16.dp)
            start.linkTo(year.end, margin = 16.dp)
            baseline.linkTo(year.baseline)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }, textAlign = TextAlign.Start)
        Text(text = listing.model, modifier = Modifier.constrainAs(model) {
            top.linkTo(make.top, margin = 16.dp)
            start.linkTo(make.end, margin = 16.dp)
            baseline.linkTo(make.baseline)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        })
        Text(text = listing.trim, modifier = Modifier.constrainAs(trim) {
            top.linkTo(model.top, margin = 16.dp)
            start.linkTo(model.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            baseline.linkTo(model.baseline)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        })
        Text(
            text = stringResource(id = R.string.dollar_sign) + listing.currentPrice.toString(),
            modifier = Modifier.constrainAs(price) {
                top.linkTo(year.bottom, margin = 16.dp)
                start.linkTo(year.start)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })
        Text(
            text = listing.currentPrice.toString() + stringResource(id = R.string.mileage_symbol),
            modifier = Modifier.constrainAs(mileage) {
                top.linkTo(price.top, margin = 16.dp)
                start.linkTo(price.end, margin = 16.dp)
                baseline.linkTo(price.baseline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )
        Text(
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

@Preview
@Composable
fun ProfileCardPreview() {
    val viewModel: CarListViewModel by viewModel()
    val listing = viewModel.carListings.observeAsState(emptyList())
    for (list in listing.value) {
        ProfileCard(listing = list)
    }
}

@Composable
private fun AppBar(viewModel: CarListViewModel) {
    val listings = viewModel.carListings.observeAsState(emptyList())
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val serviceStatus = viewModel.serviceStatus.observeAsState(ServiceStatus.IDLE)
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            when (serviceStatus.value) {
                ServiceStatus.IDLE, ServiceStatus.LOADING -> {
                    LoadingAnimations()
                }
                ServiceStatus.DONE -> {
                    LoadItemList(listings = listings)
                }
                else -> {
                    LoadingAnimations()
                }

            }
        },
        bottomBar = {
            BottomAppBar(backgroundColor = MaterialTheme.colors.primary) {
                Text(
                    stringResource(id = R.string.app_name)
                )
            }
        }
    )
}

@Composable
fun LoadItemList(listings: State<List<Listings>>) {
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
                ProfileContent(listing = listing, modifier = Modifier.wrapContentSize())
            }

        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun LoadingAnimations() {

    val image = AnimatedImageVector.animatedVectorResource(R.drawable.loading_animation)
    val atEnd by remember { mutableStateOf(false) }
    Icon(
        painter = rememberAnimatedVectorPainter(image, atEnd),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Preview
@Composable
fun LoadingAnimationsPreview() {
    Box(modifier = Modifier.wrapContentSize()){

        val image = AnimatedImageVector.animatedVectorResource(R.drawable.loading_animation)
        var atEnd by remember { mutableStateOf(false) }
        Image(
            painter = rememberAnimatedVectorPainter(image, atEnd),
            contentDescription = stringResource(id = R.string.imageloader),
            modifier = Modifier.wrapContentSize(align = Alignment.Center)

        )
        DisposableEffect(Unit) {
            atEnd = !atEnd
            onDispose {
            }
        }
    }

}