package uz.abdullajon.onlineshopping.ui.home.homeA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_1.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Brand
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.ui.home.adapters.BrandAdapter
import uz.abdullajon.onlineshopping.ui.home.adapters.BrandListener
import uz.abdullajon.onlineshopping.ui.home.adapters.ProductAdapter

class HomeAFragment : Fragment(), HomeAContract.HomeFragmentView, BrandListener {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var brandAdapter: BrandAdapter
    private var products = listOf<Product>()
    private var presenter: HomeAPresenterImpl? = null

    private var categoryId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            categoryId = requireArguments().getInt("categoryId", 0)
        }
        presenter = HomeAPresenterImpl(this)
        presenter!!.loadData(categoryId)
        initializeUI()
    }

    private fun initializeUI() {
        product_list.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(requireContext(), this::onItemClick)
        product_list.adapter = productAdapter
        brandAdapter = BrandAdapter(requireContext(), this)
        brand_list.adapter = brandAdapter

    }

    private fun onItemClick(productId: Int) {
        val bundle = bundleOf("productId" to productId)
        if (categoryId == 0) {
            findNavController().navigate(
                R.id.action_navigation_home_to_navigation_product_item,
                bundle
            )
        } else {
            findNavController().navigate(
                R.id.action_homeAFragment2_to_navigation_product_item,
                bundle
            )
        }
    }

    override fun onResponse(
        brands: List<Brand>,
        products: List<Product>
    ) {
        this.products = products
        brandAdapter.submitList(brands)
        productAdapter.submitList(products)
    }


    override fun onFail(exception: String) {
        Snackbar.make(search, exception, Snackbar.LENGTH_LONG)
            .show()
    }


    override fun hideProgress() {
        product_list.visibility = View.VISIBLE
        brand_list.visibility = View.VISIBLE
        tv_name_brand.visibility = View.VISIBLE
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showProgress() {
    }

    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }

    override fun itemClick(position: Int) {
        val data = mutableListOf<Product>()
        products.forEach {
            if (it.brandID == position) {
                data.add(it)
            }
        }
        if (position == 0) {
            productAdapter.submitList(products)
        } else {
            productAdapter.submitList(data)
        }
    }
}