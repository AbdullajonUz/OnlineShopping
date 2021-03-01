package uz.abdullajon.onlineshopping.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Order(
    val customerID: String?,
    val amount: Long?,
    val state: String?,
    val orderDate: String?,
    val shipDate: String?,
    val payment: Payment?,
    val orderList: List<OrderDetail>?,
    val address: String?,
    var orderId: String = ""
):Serializable