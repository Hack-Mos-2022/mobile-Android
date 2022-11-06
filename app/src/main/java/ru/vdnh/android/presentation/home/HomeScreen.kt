package ru.vdnh.android.presentation.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import ru.vdnh.android.domain.model.Advertisement
import ru.vdnh.android.domain.model.FoodItem
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.presentation.components.PlaceCard
import ru.vdnh.android.presentation.components.SearchBar
import ru.vdnh.android.presentation.home.components.ChipBar
import ru.vdnh.android.presentation.home.components.VdnhBottomNavigation
import ru.vdnh.android.presentation.util.Screen
import ru.vdnh.android.R
import ru.vdnh.android.presentation.map.MapboxMap
import java.util.Calendar

@Composable
fun HomeScreen(
    scrollState: LazyListState,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current as Activity

    context.window.statusBarColor = Color.Gray.toArgb()
    context.window.navigationBarColor = Color.White.toArgb()

    val homeScreenState by viewModel.homeScreenState

//    HomeView(scrollState = scrollState) {
//        navController.navigate(Screen.Profile.route)
//    }
//    MapScreen()
    MapboxMap(
        modifier = Modifier,
        mapInitOptions = MapInitOptions(
            context = context,
            cameraOptions = CameraOptions.Builder()
                .center(Point.fromLngLat(37.628951, 55.831788))
                .zoom(12.0).build(),
//            resourceOptions = ResourceOptions.Builder().
        )
    ) {

    }
}

@Composable
fun HomeView(
    scrollState: LazyListState,
    homeScreenState: HomeScreenState,
    onTopSectionClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = scrollState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            TopSection { onTopSectionClick() }
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            GreetingSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Column(
                modifier = Modifier.padding(8.dp, 0.dp)
            ) {
                SearchBar()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            AdSection(homeScreenState.adsList)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            RecommendedSection(homeScreenState.foodList)
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (homeScreenState.likedPlaceList.isNotEmpty()) {
            item {
                FavouriteSection(homeScreenState.likedPlaceList) {
//                    viewModel.onEvent(HomeScreenEvent.SelectPlace(it) {
//                        navController.navigate(Screen.PlaceDetails.route)
//                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            ChipBar()
        }
        item {
            MainSection()
        }
        items(homeScreenState.placeList.size) {
            PlaceCard(
                place = homeScreenState.placeList[it],
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
//                        viewModel.onEvent(HomeScreenEvent.SelectPlace(homeScreenState.placeList[it]) {
//                            navController.navigate(Screen.PlaceDetails.route)
//                        })
                    }
            )
        }
        item {
            ThankYouSection()
        }

    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    VdnhBottomNavigation(
        backgroundColor = Color(0xFFE1E1E1)

    ) {
        BottomNavigationItem(
            icon =
            {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                )
            },

            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White,
            alwaysShowLabel = false,
            selected = currentRoute == Screen.Home.route,

            onClick = {
                navController.navigate(Screen.Home.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        )

        Row() {
            Spacer(modifier = Modifier.width(56.dp))
        }


        BottomNavigationItem(
            icon =
            {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_assignment_24),
                    contentDescription = "History",
                )
            },

            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White,
            alwaysShowLabel = false,
            selected = currentRoute == Screen.History.route,
            onClick = {
                navController.navigate(Screen.History.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        )

    }

}


@Composable
fun MainSection() {
    Column(modifier = Modifier.padding(8.dp, 0.dp))
    {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "All around you..",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
}


@Composable
fun FavouriteSection(
    list: List<Place>,
    onClick: (place: Place) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp, 0.dp))
    {
        Text(
            text = "Order from favourites..",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))

    }

    LazyRow(
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list.size) {
            FavouriteCard(place = list[it], modifier = Modifier.clickable {
                onClick(list[it])
            })
        }


    }
}

@Composable
fun FavouriteCard(
    place: Place,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = place.image), contentDescription = "Place",
            modifier = Modifier
                .size(100.dp, 130.dp)
                .shadow(elevation = 0.dp, shape = RoundedCornerShape(8.dp), clip = true),
            contentScale = ContentScale.Crop
        )
        Text(text = place.name)

    }
}

@Composable
fun RecommendedSection(list: List<FoodItem>) {
    Column(modifier = Modifier.padding(8.dp, 0.dp))
    {
        Text(
            text = "Recommended for you...",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    LazyRow(
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list.size) {
            RecommendedCard(foodItem = list[it])
        }
    }
}

@Composable
fun RecommendedCard(
    foodItem: FoodItem
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = foodItem.image), contentDescription = "Place",
            modifier = Modifier
                .size(80.dp)
                .shadow(elevation = 0.dp, shape = CircleShape, clip = true),
            contentScale = ContentScale.Crop
        )
        Text(text = foodItem.name)
    }
}


@Composable
fun TopSection(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = stringResource(id = R.string.display_picture)
        )


        Row(modifier = Modifier.clickable { }) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = stringResource(R.string.location),

                    )
                Text(text = "Guwahati")
            }
        }

    }

}

@Composable
fun GreetingSection(
    userName: String = "Andrew"
) {
    val c: Calendar = Calendar.getInstance()
    val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)
    Column(modifier = Modifier.padding(16.dp, 0.dp)) {
        Text(
            text = when (timeOfDay) {
                in 0..11 -> {
                    "Good Morning!"
                }

                in 12..15 -> {
                    "Good Afternoon!"
                }

                else -> {
                    "Good Evening!"
                }
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = userName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light
        )
    }
}


@Composable
fun AdSection(
    adsList: List<Advertisement>
) {
    Column()
    {

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(adsList.size) {
                AdCard(
                    adsList[it]
                )
            }
        }
    }
}


@Composable
fun AdCard(
    ad: Advertisement
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = ad.color
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .size(210.dp, 140.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                Text(
                    text = ad.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = ad.subTitle,
                    fontWeight = FontWeight.Light
                )
            }
            Image(
                painter = painterResource(id = ad.image),
                contentDescription = "Ad",
                modifier = Modifier
                    .size(150.dp)
                    .weight(0.5f)
            )
        }

    }

}

@Composable
fun ThankYouSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.with),
            modifier = Modifier.alpha(0.5f),
            fontFamily = FontFamily.Cursive,
            fontSize = 24.sp
        )
        Icon(
            imageVector = Icons.Filled.Favorite, contentDescription = stringResource(R.string.love),
            tint = Color.Red,
            modifier = Modifier.size(32.dp)
        )
    }
}

