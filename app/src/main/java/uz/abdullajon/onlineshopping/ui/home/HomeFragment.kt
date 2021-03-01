package uz.abdullajon.onlineshopping.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.ui.home.homeA.HomeAFragment
import uz.abdullajon.onlineshopping.ui.home.homeB.HomeBFragment


class HomeFragment : Fragment(), HomeContract.HomeFragmentView {

    lateinit var pagerAdapter: MyPagerAdapter

    //    lateinit var sharedViewModel: SharedViewModel
    private var presenter: HomePresenterImpl? = null
    private val products = mutableListOf<Product>()
    lateinit var dataAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenterImpl(this)
        addPager()
        //         sharedViewModel = SharedViewModel()
//        sharedViewModel.price.observe(viewLifecycleOwner,
//            Observer {
//                val bundle = bundleOf("categoryId" to it)
//                findNavController().navigate(R.id.action_navigation_home_to_homeAFragment2,bundle)
//            })


        dataAdapter = SearchAdapter(
            requireActivity(), R.layout.search_item, products
        )
        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { presenter!!.loadData(it) }
                return true
            }

        })

        val searchAutoComplete: SearchView.SearchAutoComplete =
            search.findViewById(androidx.appcompat.R.id.search_src_text)
        search.setBackgroundColor(Color.WHITE)
        searchAutoComplete.setAdapter(dataAdapter)

        searchAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val bundle = bundleOf("productId" to products[position].productID)
                findNavController().navigate(
                    R.id.action_navigation_home_to_navigation_product_item,
                    bundle
                )
            }
    }

    private fun addPager() {
        pagerAdapter = MyPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(HomeAFragment())
        pagerAdapter.addFragment(HomeBFragment())
        tab_layout.setupWithViewPager(pager)
        pager.adapter = pagerAdapter
    }

    override fun onStop() {
        super.onStop()
        presenter!!.cancel()
    }

    override fun onResponse(products: List<Product>) {
        products.forEach {
            Log.d("adddd", it.productName!!)
        }
        this.products.clear()
        this.products.addAll(products)
        dataAdapter.notifyDataSetChanged()
    }

    override fun onFail(exception: String) {

    }
}