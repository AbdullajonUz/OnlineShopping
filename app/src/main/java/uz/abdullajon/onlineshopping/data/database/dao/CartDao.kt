package uz.abdullajon.onlineshopping.data.database.dao

import androidx.room.*
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.utils.BooleanToIntConverter

@Dao
@TypeConverters(BooleanToIntConverter::class)
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCartProduct(cartModel: CartModel)

    @Query("select * from CartModel")
    fun getCartProducts(): List<CartModel>


    @Query("Update CartModel set quantity = :quantity WHERE id = :id")
    fun updateQuantityProduct(quantity: Int, id: Int)

    @Query("DELETE FROM CartModel WHERE id = :id")
    fun deleteItemById(id: Long)
}


