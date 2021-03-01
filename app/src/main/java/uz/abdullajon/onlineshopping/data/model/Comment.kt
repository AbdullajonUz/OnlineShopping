package uz.abdullajon.onlineshopping.data.model

data class Comment(
    val commentID: Int,
    val customerID: Int,
    val productID: Int,
    val message: String
)