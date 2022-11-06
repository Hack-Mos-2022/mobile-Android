package ru.vdnh.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vdnh.android.domain.model.Place
import ru.vdnh.android.R
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

@Composable
fun PlaceCard(
    place: Place,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                ,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = "Place",
                modifier = Modifier
                    .size(130.dp, 170.dp)
                    .shadow(elevation = 0.dp, shape = RoundedCornerShape(8.dp), clip = true),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = place.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
// TODO                    Image(
//                        painter = painterResource(id = R.drawable.favorite),
//                        contentDescription = "Rating",
//                        modifier = Modifier
//                            .size(20.dp)
//                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Text(text = "${place.rating}(${getCustomerInfo(place.noOfRatings)}) ")
                    Text(
                        text = "·",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = " ${getTimeInMins(place.timeInMillis)}")
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
            }

        }
        Spacer(modifier = Modifier.height(18.dp))

    }


}

fun getTimeInMins(timeInMillis: Long): String {
    var millis = timeInMillis
    require(millis >= 0) { "Less than a minute" }
    val days: Long = TimeUnit.MILLISECONDS.toDays(millis)
    millis -= TimeUnit.DAYS.toMillis(days)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val sb = StringBuilder(64)
    if (hours > 0) {
        sb.append(hours)
        sb.append(" Hr ")
    }
    sb.append(minutes)
    sb.append(" Min ")
    return sb.toString()
}

fun getCustomerInfo(noOfRatings: Int): String {
    return when {
        noOfRatings > 50 -> {
            "50+"
        }
        noOfRatings > 100 -> {
            "100+"
        }
        noOfRatings > 150 -> {
            "150+"
        }
        noOfRatings > 200 -> {
            "200+"
        }
        noOfRatings > 250 -> {
            "250+"
        }
        noOfRatings > 300 -> {
            "300+"
        }
        noOfRatings > 350 -> {
            "350+"
        }
        noOfRatings > 400 -> {
            "400+"
        }
        noOfRatings > 450 -> {
            "450+"
        }
        noOfRatings > 500 -> {
            "500+"
        }
        noOfRatings > 550 -> {
            "550+"
        }
        noOfRatings > 600 -> {
            "600+"
        }
        noOfRatings > 650 -> {
            "650+"
        }
        noOfRatings > 700 -> {
            "700+"
        }
        noOfRatings > 750 -> {
            "750+"
        }
        noOfRatings > 800 -> {
            "800+"
        }
        else -> {
            "1000+"
        }
    }
}



