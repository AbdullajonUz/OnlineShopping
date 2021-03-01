package uz.abdullajon.onlineshopping.ui.home.homeB

import uz.abdullajon.onlineshopping.data.model.Brand
import uz.abdullajon.onlineshopping.data.model.Category
import uz.abdullajon.onlineshopping.data.model.Product

interface HomeBContract {
    interface HomeFragmentView {
       fun onResponse(
            categories: List<Category>
        )

        fun onFail(exception: String)
    }

    interface HomeFragmentPresenter {
        fun loadData()
        fun cancel()
    }
}