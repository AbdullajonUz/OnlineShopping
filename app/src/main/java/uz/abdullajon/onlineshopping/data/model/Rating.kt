package uz.abdullajon.onlineshopping.data.model

data class Rating(
    val ratingID: Int,
    val customerID: Int,
    val productID: Int,
    val productRating: Float
)