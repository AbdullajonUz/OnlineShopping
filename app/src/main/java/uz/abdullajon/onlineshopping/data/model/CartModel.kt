package uz.abdullajon.onlineshopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartModel(
    val productName: String?,
    val productImage: String?,
    val productPrice: Long?,
    var quantity: Int?,
    @PrimaryKey(autoGenerate = false)
    val id: Int
)
