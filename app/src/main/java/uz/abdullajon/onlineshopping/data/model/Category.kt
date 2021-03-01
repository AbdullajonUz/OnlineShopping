package uz.abdullajon.onlineshopping.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val categoryID: Int,
    val categoryName: String?,
    val categoryImage:String?
) : Parcelable {
    constructor() : this(0, "","")
}