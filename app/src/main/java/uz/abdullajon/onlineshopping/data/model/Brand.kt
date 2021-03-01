package uz.abdullajon.onlineshopping.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Brand(
    val brandID: Int,
    val brandName: String,
    val brandImage: String
) : Parcelable