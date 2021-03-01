package uz.abdullajon.onlineshopping.ui.home.review

interface ReviewMVP {
    interface ReviewView {
        //        fun onResponse(review: Review?)
        fun onResponse()

        fun hideProgress()
        fun showProgress()
        fun onFail()
    }

    interface ReviewPresenter {
        fun initData(title: String, rating: Float, productId: Int)
        //        fun loadData(productId: Int)
        fun cancel()
    }
}