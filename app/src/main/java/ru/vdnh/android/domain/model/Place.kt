package ru.vdnh.android.domain.model

data class Place(
    val name: String,
    val rating: Double,
    val noOfRatings: Int,
    val timeInMillis: Long,
    val variety: String,
    val place: String,
    val averagePrice: Double,
    val image: Int,
    val imageId: Int,
    val events: List<EventItem> = listOf()
)
