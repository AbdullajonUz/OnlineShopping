package uz.abdullajon.onlineshopping.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val priceLiveData = MutableLiveData<Int>()
    fun setPrice(price: Int) {
        priceLiveData.value = price
    }

    val price: LiveData<Int>
        get() = priceLiveData
}