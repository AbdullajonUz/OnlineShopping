package uz.abdullajon.onlineshopping.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CustomerCard(
    val cardName: String?,
    val cardNumber: String?,
    val expiryDate: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable