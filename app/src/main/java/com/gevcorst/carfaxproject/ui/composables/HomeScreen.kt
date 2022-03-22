package com.gevcorst.carfaxproject.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.gevcorst.carfaxproject.R
import com.gevcorst.carfaxproject.model.Listings
import com.gevcorst.carfaxproject.viewmodel.CarListViewModel
import com.gevcorst.carfaxproject.viewmodel.ServiceStatus
import com.skydoves.landscapist.glide.GlideImage

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
    Surface() {
        ConstraintLayout(modifier = Modifier.wrapContentSize()) {
            val (image, contents) = createRefs()
            ProfilePicture(carListing = listing, modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(contents.start)
            })
            ProfileContent(listing = listing, modifier = Modifier.constrainAs(contents) {
                top.linkTo(image.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })
        }
    }
}

@Composable
fun ProfilePicture(carListing: Listings, modifier: Modifier) {
    Row(modifier = Modifier.size(130.dp)) {
        val url = remember{carListing.images.firstPhoto.medium}
        val painter =
            rememberAsyncImagePainter(ImageRequest.Builder //Used while loading
                (LocalContext.current).data(data =url).apply(block = fun ImageRequest.Builder.() {
                crossfade(true) //Crossfade animation between images
                placeholder(R.drawable.loading_animation) //Used while loading
                fallback(R.drawable.ic_baseline_broken_image_24) //Used if data is null
                error(R.drawable.ic_baseline_broken_image_24) //Used when loading returns with error
            }).build()
            )


        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            //Use painter in Image composable
            painter = painter,
            contentDescription = stringResource(id = R.string.imageloader)
        )
    }
}

@Composable
fun ProfileContent(listing: Listings, modifier: Modifier) {
    ConstraintLayout(modifier = Modifier.wrapContentSize()) {
        val (year, make, model, trim, price, mileage, location) = createRefs()
        Text(text = listing.year.toString(), modifier = Modifier.constrainAs(year) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(make.start, margin = 16.dp)
        }, textAlign = TextAlign.Start)
        Text(text = listing.make.toString(), modifier = Modifier.constrainAs(make) {
            top.linkTo(year.top, margin = 16.dp)
            end.linkTo(model.start, margin = 16.dp)
        })
        Text(text = listing.model.toString(), modifier = Modifier.constrainAs(model) {
            top.linkTo(make.top, margin = 16.dp)
            end.linkTo(trim.start, margin = 16.dp)
        })
        Text(text = listing.trim.toString(), modifier = Modifier.constrainAs(trim) {
            top.linkTo(make.top, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        })
        Text(
            text = stringResource(id = R.string.dollar_sign) + listing.currentPrice.toString(),
            modifier = Modifier.constrainAs(price) {
                top.linkTo(year.bottom, margin = 16.dp)
                start.linkTo(year.start, margin = 16.dp)
                end.linkTo(mileage.start)
            })
        Text(
            text = listing.currentPrice.toString() + stringResource(id = R.string.mileage_symbol),
            modifier = Modifier.constrainAs(mileage) {
                top.linkTo(price.top, margin = 16.dp)
                end.linkTo(trim.end, margin = 16.dp)
            },
            textAlign = TextAlign.Start
        )
        Text(
            text = listing.dealer.city + " " + listing.dealer.state,
            modifier = Modifier.constrainAs(location) {
                top.linkTo(price.bottom, margin = 16.dp)
                start.linkTo(price.start)
                end.linkTo(mileage.end, margin = 16.dp)
            }, textAlign = TextAlign.Start
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
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        drawerContent = { Text(text = "Drawer Menu 1") },
        content = {
            Divider(modifier = Modifier.padding(10.dp))
            val serviceStatus = viewModel.serviceStatus.observeAsState(ServiceStatus.IDLE)
            CustomCircularProgressBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                when (serviceStatus.value) {
                    ServiceStatus.IDLE, ServiceStatus.LOADING -> {
                        items(listings.value) { listing ->
                            CustomCircularProgressBar()
                        }
                    }
                    ServiceStatus.DONE -> {
                        items(listings.value) { listing ->
                            ProfileCard(listing = listing)
                        }
                    }
                    else -> {

                    }

                }

            }
        },
        bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primary) { Text("Bottom App Bar") } }
    )
}

@Composable
fun ListRow(carList: Listings) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(carList.images.firstPhoto.large)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.loading_animation),
            contentDescription = stringResource(R.string.imageloader),
            contentScale = ContentScale.Inside,
            error = painterResource(R.drawable.ic_baseline_broken_image_24),
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp)
        )

        Column() {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = carList.year.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = carList.make,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = carList.model,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = carList.trim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row() {
                Text(
                    text = carList.currentPrice.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = carList.mileage.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )

            }
            Text(
                text = carList.dealer.city + " " + carList.dealer.state,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,

                )
            SimpleButton(text = stringResource(R.string.call_dealer)) {

            }
        }
    }
}

@Composable
fun SimpleButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleButtonPrev(text: String = "Hello", onClick: () -> Unit = {}) {
    ConstraintLayout {
        val context = LocalContext.current
        val clickAction = {
            Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show()
        }
        Spacer(modifier = Modifier.size(16.dp))
        val (button, text) = createRefs()
        Button(

            onClick = clickAction, modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .wrapContentHeight()
                .wrapContentWidth()
        ) { Text(text = "click me !") }
        Text(
            text = "Hello world",
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(button.bottom)
                    start.linkTo(button.start)
                }
                .wrapContentSize(Alignment.TopStart),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.W900,
            fontFamily = FontFamily.Default
        )
    }
}

@Composable
fun CustomCircularProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier.size(width = 100.dp, height = 100.dp),
        color = MaterialTheme.colors.primary,
        strokeWidth = 10.dp
    )

}
