package uz.abdullajon.onlineshopping.ui.home.product

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_product.*
import kotlinx.android.synthetic.main.fragment_product.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.data.model.Review
import uz.abdullajon.onlineshopping.ui.base.BaseFragment
import uz.abdullajon.onlineshopping.ui.home.adapters.ReviewAdapter
import uz.abdullajon.onlineshopping.utils.PATH_URL
import uz.abdullajon.onlineshopping.utils.SuccessDialog


class ProductFragment : BaseFragment(), ProductItemContract.ProductItemView {

    private var product: Product = Product()
    private var presenter: ProductItemPresenterImpl? = null
    private var dialog: AlertDialog? = null
    private lateinit var reviewAdapter: ReviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun layoutRes(): Int = R.layout.fragment_product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ProductItemPresenterImpl(this)
        val id = arguments?.getInt("productId")
        if (id != null) {
            presenter?.loadData(id)
        }

        reviewAdapter = ReviewAdapter(requireContext())
        review_list.adapter = reviewAdapter
        tv_write_review.setOnClickListener {
            val productName: String? = product.productName
            val bundle = bundleOf("productName" to productName, "productId" to id)
            findNavController().navigate(
                R.id.action_navigation_product_item_to_reviewFragment,
                bundle
            )
        }
        add_btn.setOnClickListener { addProduct() }
    }

    private fun addProduct() {
//        val fromsmall = AnimationUtils.loadAnimation(requireContext(), R.anim.fromsmall)

        val successDialog = SuccessDialog(requireContext())
        successDialog.show()

        if (product.productName != null) {
            val cartModel =
                CartModel(
                    product.productName,
                    product.imageUrl,
                    product.price,
                    1,
                    product.productID
                )
            presenter?.addCartProduct(cartModel, requireContext())
        }
    }

    override fun successAddProduct() {
        //findNavController().navigate(R.id.action_navigation_product_item_to_navigation_cart)
    }

    override fun onResponse(
        productItem: Product?,
        review: List<Review>?
    ) {
        if (productItem != null) {
            product = productItem
            if (activity!=null) {
                Glide
                    .with(this)
                    .load(PATH_URL + product.imageUrl)
                    .into(product_image)
            }
            reviewAdapter.submitList(review)
            tv_product_price.text = productItem.price.toString()
            tv_product_name.text = productItem.productName
            tv_product_detail.text = productItem.description
            //TODO("add date and rating")
        } else {
            Snackbar.make(tv_product_price, "product null item", Snackbar.LENGTH_LONG).show()
        }

    }

    override fun hideProgress() {
        dialog?.hide()
    }

    override fun showProgress() {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_progress)
            .setCancelable(true)
            .setOnDismissListener { dialog -> dialog.dismiss() }
        dialog = builder.create()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }
}