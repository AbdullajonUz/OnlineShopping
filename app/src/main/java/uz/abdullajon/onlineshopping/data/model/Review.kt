package uz.abdullajon.onlineshopping.data.model

data class Review(
    val reviewId: String?,
    val productId: Int?,
    val title: String?,
    val rating: Float?,
    val customerName: String?
) {
    constructor() : this("",0, "", 0f, "")
}