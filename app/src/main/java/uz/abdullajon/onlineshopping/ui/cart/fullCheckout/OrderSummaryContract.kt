package uz.abdullajon.onlineshopping.ui.cart.fullCheckout

import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.data.model.Order

interface OrderSummaryContract {
    interface CartView {
        fun getResponse(list: List<CartModel>)
        fun hideProgress()
        fun showProgress()
        fun onFail(exception: String)
        fun successAuth()

    }

    interface CartPresenter {
        fun getCartProduct()
        fun setOrder(order: Order)
        fun onCancel()
    }
}