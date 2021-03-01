package uz.abdullajon.onlineshopping.utils

import androidx.room.TypeConverter

class BooleanToIntConverter {

    @TypeConverter
    fun intToBoolean(it: Int): Boolean {
        return it == 1
    }

    @TypeConverter
    fun booleanToInt(it: Boolean): Int {
        return if (it) 1
        else 0
    }
}