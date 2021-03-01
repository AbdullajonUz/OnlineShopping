package uz.abdullajon.onlineshopping.data.database.dao

import androidx.room.*
import uz.abdullajon.onlineshopping.data.model.CustomerCard
import uz.abdullajon.onlineshopping.utils.BooleanToIntConverter

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: CustomerCard)

    @Query("select * from CustomerCard")
    fun getGard(): List<CustomerCard>


    @Query("DELETE FROM CustomerCard WHERE id = :id")
    fun deleteItemById(id: Long)
}


