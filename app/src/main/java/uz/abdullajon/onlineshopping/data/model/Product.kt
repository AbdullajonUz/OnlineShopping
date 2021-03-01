package uz.abdullajon.onlineshopping.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val productID: Int,
    val productName: String?,
    val price: Long?,
    val description: String?,
    val rating: Float?,
    val productStock: Float?,
    val ProductDate: String?,
    val categoryID: Int?,
    val brandID: Int?,
    val imageUrl: String?
) : Parcelable {

    constructor(productID: Int, productName: String) : this(
        productID,
        productName,
        0,
        "",
        0f,
        0f,
        "",
        0,
        0,
        ""
    )

    constructor() : this(0, "", 0, "", 0f, 0f, "", 0, 0, "")
}