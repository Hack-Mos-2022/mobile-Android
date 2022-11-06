package ru.vdnh.android.presentation.cart

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.vdnh.android.domain.model.CartItem
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.presentation.components.getTimeInMins
import ru.vdnh.android.R
import kotlin.math.round

@Composable
fun Cart(
    navController: NavHostController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val list by viewModel.cartState


    val context = LocalContext.current as Activity

    context.window.statusBarColor = Color.Gray.toArgb()
    context.window.navigationBarColor = Color.White.toArgb()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE8E7E7)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = "Back")
            }
        }

        ItemSection(
            list = list.list.filter {
                it.noOfItems > 0
            },
            onDecreaseClick = { viewModel.onEvent(CartEvent.DecreaseCartQuantity(it)) },
            onIncreaseClick = { viewModel.onEvent(CartEvent.IncreaseCartQuantity(it)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        CouponBar()
        DeliverySection(
            Place(
                name = "Relish",
                rating = 3.9,
                noOfRatings = 258,
                timeInMillis = 1800000,
                variety = "American, French",
                place = "Misamari",
                averagePrice = 1.0,
                imageId = 0,
                image = 0,
            )
        )
        BillSection(
            itemTotal = 6.09
        )
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

@Composable
fun BillSection(
    itemTotal: Double
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = 16.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Bill  Details", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(text = "Item total:")
                        Text(text = "Taxes and charges:")
                        Text(text = "Total:")
                    }
                    Column {
                        Text(text = "$$itemTotal")
                        Text(text = "$${((0.18) * itemTotal).round(2)}")
                        Text(text = "$${(itemTotal + ((0.18) * itemTotal).round(2)).round(2)}")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Proceed to Pay")
                    }
                }

            }
        }
    }
}

@Composable
fun DeliverySection(
    place: Place
) {
    Column(modifier = Modifier.padding(16.dp)) {


        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = 16.dp

        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Deliver to:", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Back",
                            modifier = Modifier.alpha(0.5f),
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(text = "Location:")
                        Text(text = "Estimated time:")
                    }
                    Column {
                        Text(text = "Guwahati")
                        Text(text = getTimeInMins(place.timeInMillis))
                    }


                }

            }
        }
    }
}

@Composable
fun ItemSection(
    list: List<CartItem>,
    onIncreaseClick: (cartItem:CartItem) -> Unit,
    onDecreaseClick: (cartItem:CartItem) -> Unit

) {
    Column(modifier = Modifier.padding(16.dp)) {

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = 16.dp
        ) {
            Column(Modifier.padding(16.dp)) {
                if (list.isNotEmpty()) {
                    LazyColumn() {
                        items(list.size) {
                            CartItemCard(
                                cartItem = list[it],
                                onIncreaseClick = { onIncreaseClick(list[it]) },
                                onDecreaseClick = { onDecreaseClick(list[it]) }
                            )
                        }

                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text(text = "Empty")
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp)
                )

                val inputValue = remember { mutableStateOf("") }
                val hintState = remember {
                    mutableStateOf(true)
                }

                Box {
                    BasicTextField(
                        value = inputValue.value,
                        onValueChange = { inputValue.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .onFocusChanged {
                                if (inputValue.value == "") {
                                    hintState.value = !it.isFocused
                                }
                            }
                    )

                    if (hintState.value) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Write instruction for place...",
                                modifier = Modifier.alpha(0.5f),
                            )
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Back",
                                modifier = Modifier.alpha(0.5f),
                            )

                        }
                    }
                }
            }
        }

    }
}


@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = cartItem.eventItem.event)
        }

        if (
            cartItem.noOfItems == 0
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row {
                    Text(
                        text = "Add",
                        modifier = Modifier.clickable { },
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        } else {
            Column(

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
                        Text(text = cartItem.noOfItems.toString())
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

        Text(text = "  $  ${cartItem.eventItem.price}", overflow = TextOverflow.Ellipsis)


    }
}

@Composable
fun CouponBar() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,

            ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "Add a coupon code...",
                        modifier = Modifier.alpha(0.5f)
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 16.dp, shape = CircleShape),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.White,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            Row {
                Text(
                    text = "APPLY",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { },
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}
