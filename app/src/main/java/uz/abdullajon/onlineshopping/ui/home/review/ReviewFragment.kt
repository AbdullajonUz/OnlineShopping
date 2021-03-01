package uz.abdullajon.onlineshopping.ui.home.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_write_review.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.ui.base.BaseFragment

class ReviewFragment : BaseFragment(), ReviewMVP.ReviewView {

    override fun layoutRes(): Int = R.layout.fragment_write_review
    private lateinit var presenter: ReviewPresenterImpl
    private var productId: Int? = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ReviewPresenterImpl(this)
        productId = arguments?.getInt("productId")
        val productName = arguments?.getString("productName")
        if (productName != null) {
            tv_product_name.text = productName
        }
        btn_send.setOnClickListener {
            if (!et_review_title.text.isNullOrEmpty() && productId != null) {
                btn_send.isEnabled = false
                presenter.initData(
                    et_review_title.text.toString(),
                    rating_bar.rating,
                    productId!!
                )
            } else {
                Toast.makeText(requireContext(), "please complete all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

//    override fun onResponse(review: Review?) {
//        if (review != null) {
//            rating_bar.rating = review.rating!!
//            et_review_title.text = review.title as Editable
//        }
//    }

    override fun onResponse() {
//        val bundle = bundleOf("productId" to productId)
        findNavController().popBackStack(R.id.navigation_product_item, true)
    }

    override fun onFail() {

    }

    override fun hideProgress() {

    }

    override fun showProgress() {

    }


    override fun onStop() {
        super.onStop()
        presenter.cancel()
    }

}