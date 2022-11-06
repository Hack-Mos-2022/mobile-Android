package ru.vdnh.android.presentation.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vdnh.android.R
import ru.vdnh.android.domain.model.EventItem
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.presentation.components.getCustomerInfo
import ru.vdnh.android.presentation.components.getTimeInMins
import ru.vdnh.android.presentation.util.Screen

@Composable
fun PlaceDetail(
    navController: NavHostController,
    viewModel: PlaceDetailViewModel = hiltViewModel()
) {

    val detailScreenState by viewModel.detailScreenState


    val rotationState by animateFloatAsState(
        targetValue = if (detailScreenState.recommendedExpandedState) 180f else 0f
    )

    Box(contentAlignment = Alignment.BottomEnd) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                    Row {
                        IconToggleButton(
                            checked = detailScreenState.isLiked,
                            onCheckedChange = {
                                viewModel.onEvent(DetailScreenEvent.ToggleLikedStatus)

                            },
                        ) {
                            if (detailScreenState.isLiked) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Favourite",
                                    tint = Color.Red
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Favourite"
                                )
                            }

                        }
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Outlined.Share, contentDescription = "Share")
                        }

                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    PlaceDetailCard(
                        detailScreenState.place!!
                    )

                }
            }
            val recommendedList =
                detailScreenState.recommendedList




            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = "Recommended",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                        onClick = {
                            viewModel.onEvent(DetailScreenEvent.ToggleRecommendedSectionExpandedState)
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }

                }

            }
            if (detailScreenState.recommendedExpandedState) {

                items(recommendedList.size) {
                    MenuItemCard(
                        eventItem = recommendedList[it].eventItem,
                        noOfItems = recommendedList[it].noOfItems,
                        onDecreaseClick = { viewModel.onEvent(DetailScreenEvent.DecreaseCartQuantity(recommendedList[it])) },
                        onIncreaseClick = {viewModel.onEvent(DetailScreenEvent.IncreaseCartQuantity(recommendedList[it])) }


                    )
                    if (it != (recommendedList.size - 1)) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp, 0.dp)
                        )
                    }
                }


            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = "Non-Vegetarian",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium),
                        onClick = {
                            // todo
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }

                }

            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = "Vegetarian",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium),
                        onClick = {
//                            todo
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }

                }

            }


        }

        if (detailScreenState.menuList.sumOf { it.noOfItems } != 0) {
            Column(modifier = Modifier.padding(16.dp)) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Cart.route)

                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Outlined.ShoppingCart, "Cart")
                }
            }
        }
    }
}


@Composable
fun MenuItemCard(
    eventItem: EventItem,
    noOfItems: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit

) {
    Row(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        Column(
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = eventItem.event)
            }

            Text(text = "  $  ${eventItem.price}", overflow = TextOverflow.Ellipsis)
            Row {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFF7A00)
                )
                Text(text = eventItem.rating.toString())
                Text(
                    text = " · ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = eventItem.noOfRatings.toString() + " ratings",
                )
            }
        }

        if (
            noOfItems == 0
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row {
                    Text(
                        text = "Add",
                        modifier = Modifier.clickable {
                            onIncreaseClick()


                        },
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Black.copy(0.5f)),
                            MaterialTheme.shapes.medium
                        ),
                    shape = MaterialTheme.shapes.medium,
                    color = Color.White,
                    contentColor = MaterialTheme.colors.primary
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            onDecreaseClick()

                        }) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Subtract",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(text = noOfItems.toString())
                        IconButton(onClick = {
                            onIncreaseClick()

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(16.dp)

                            )
                        }

                    }

                }
            }
        }
    }
}


@Composable
fun PlaceDetailCard(
    place: Place
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFFC6FDB3)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Text(
                text = place.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(2.dp))

                Text(text = "${place.rating}(${getCustomerInfo(place.noOfRatings)}) ")
                Text(
                    text = "·",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = " ${getTimeInMins(place.timeInMillis)} ")
                Text(
                    text = "·",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = " $${place.averagePrice} ")

            }
            Text(
                text = place.variety,
                modifier = Modifier.alpha(0.5f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = place.place,
                modifier = Modifier.alpha(0.5f),
            )

            Row(
                modifier = Modifier.padding(0.dp, 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            text = "Outlet:",
                            fontSize = 16.sp
                        )
                    }
                    Row {
                        Text(text = getTimeInMins(place.timeInMillis))
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(
                            text = place.place, modifier = Modifier.alpha(0.5f),
                        )
                    }
                    Row {
                        Text(
                            text = "Your Location", modifier = Modifier.alpha(0.5f),
                        )
                    }
                }
            }
        }
    }
}
