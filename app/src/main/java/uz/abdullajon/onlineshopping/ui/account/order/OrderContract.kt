package uz.abdullajon.onlineshopping.ui.account.order

import uz.abdullajon.onlineshopping.data.model.Order
import java.lang.Exception

interface OrderContract {
    interface OrderView {
        fun getResponse(order: List<Order>)
        fun onFail(exception: String)
    }

    interface OrderPresenter {
        fun loadData()
        fun onCancel()
    }
}