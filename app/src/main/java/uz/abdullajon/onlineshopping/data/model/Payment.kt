package uz.abdullajon.onlineshopping.data.model

import java.io.Serializable

data class Payment (
    val paymentType:String?,
    val paid:Boolean?,
    val cardNumber:String?,
    val cardName:String?
):Serializable