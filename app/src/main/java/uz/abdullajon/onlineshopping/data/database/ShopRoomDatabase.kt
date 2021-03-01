package uz.abdullajon.onlineshopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import uz.abdullajon.onlineshopping.data.database.dao.CardDao
import uz.abdullajon.onlineshopping.data.database.dao.CartDao
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.data.model.CustomerCard
import uz.abdullajon.onlineshopping.utils.BooleanToIntConverter


@Database(
    entities = [CartModel::class, CustomerCard::class],
    version = 3
)
@TypeConverters(BooleanToIntConverter::class)
abstract class ShopRoomDatabase : RoomDatabase() {

    abstract fun getCartDao(): CartDao
    abstract fun getCardDao(): CardDao

    companion object {
        private var cartDatabase: ShopRoomDatabase? = null

        fun getInstance(context: Context): ShopRoomDatabase {
            if (cartDatabase == null) {
                cartDatabase = Room.databaseBuilder(
                    context.applicationContext, ShopRoomDatabase::class.java, "cartDatabase"
                )
                    .addMigrations(Migration_1_2(),Migration_2_3())
                    .allowMainThreadQueries()
                    .build()
            }
            return cartDatabase!!
        }
    }

    class Migration_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE CartModel add column isSelected Integer not null default 0")
        }
    }
    class Migration_2_3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE CustomerCard ('cardName' text, 'cardNumber' text, 'expiryDate' text , 'id' Integer primary key autoincrement NOT NULL)")
            database.execSQL("DROP TABLE CartModel")
            database.execSQL("CREATE TABLE IF NOT EXISTS `CartModel` (`productName` TEXT, `productImage` TEXT, `productPrice` INTEGER, `quantity` INTEGER, `id` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        }
    }

}