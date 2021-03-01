package uz.abdullajon.onlineshopping.ui.cart

import uz.abdullajon.onlineshopping.data.model.CartModel

interface CartContract {
    interface CartView {
        fun getResponse(list: List<CartModel>)
    }

    interface CartPresenter {
        fun getCartProduct()
        fun deleteById(id: Long)
        fun quantityClick(quantity: Int, id: Int)
        fun onCancel()
    }
}