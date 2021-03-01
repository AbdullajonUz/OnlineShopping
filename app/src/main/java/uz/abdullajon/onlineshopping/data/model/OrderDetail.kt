package uz.abdullajon.onlineshopping.data.model

import java.io.Serializable

data class OrderDetail(
    val productID: Long?,
    val productName: String?,
    val price: Long?,
    val quantity: Long?
) : Serializable {
    constructor() : this(0L, "", 0L, 0L)
}