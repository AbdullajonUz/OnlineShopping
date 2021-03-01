package uz.abdullajon.onlineshopping.ui.home

import uz.abdullajon.onlineshopping.data.model.Product

interface HomeContract {
    interface HomeFragmentView {
        fun onResponse(
            products: List<Product>
        )
        fun onFail(exception: String)
    }

    interface HomeFragmentPresenter {
        fun loadData(text: String)
        fun cancel()
    }
}