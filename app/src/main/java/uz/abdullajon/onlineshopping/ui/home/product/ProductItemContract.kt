package uz.abdullajon.onlineshopping.ui.home.product

import android.content.Context
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.data.model.Review


interface ProductItemContract {

    interface ProductItemView {
        fun showProgress()
        fun hideProgress()
        fun onResponse(
            productItem: Product?,
            review: List<Review>?
        )

        fun successAddProduct()
    }


    interface ProductItemPresenter {
        fun addCartProduct(cartModel: CartModel, context: Context)
        fun loadData(id: Int)
        fun cancel()
    }
}