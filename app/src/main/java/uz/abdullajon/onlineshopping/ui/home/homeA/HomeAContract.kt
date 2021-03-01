package uz.abdullajon.onlineshopping.ui.home.homeA

import uz.abdullajon.onlineshopping.data.model.Brand
import uz.abdullajon.onlineshopping.data.model.Product

interface HomeAContract {
    interface HomeFragmentView {
        fun hideProgress()
        fun showProgress()
        fun onResponse(
            brands: List<Brand>,
            products: List<Product>
        )
        fun onFail(exception: String)
    }

    interface HomeFragmentPresenter {
        fun loadData(categoryId: Int)
        fun cancel()
    }
}