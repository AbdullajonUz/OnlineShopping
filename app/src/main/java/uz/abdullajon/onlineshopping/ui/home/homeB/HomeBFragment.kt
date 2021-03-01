package uz.abdullajon.onlineshopping.ui.home.homeB

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home_2.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Category
import uz.abdullajon.onlineshopping.ui.home.adapters.CategoryAdapter

class HomeBFragment : Fragment(), HomeBContract.HomeFragmentView {

    private lateinit var categoryAdapter: CategoryAdapter

    private var categories = mutableListOf<Category>()

    private var presenter: HomeBPresenterImpl? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomeBPresenterImpl(this)
        presenter!!.loadData()
        initializeUI()
    }

    private fun initializeUI() {
        category_list.layoutManager =
            LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter(requireContext(), this::onItemClick)
        category_list.adapter = categoryAdapter

    }


    override fun onResponse(
        categories: List<Category>
    ) {
        progress_bar.visibility = View.INVISIBLE
        category_list.visibility = View.VISIBLE
        categoryAdapter.submitList(categories)
    }


    override fun onFail(exception: String) {
        progress_bar.visibility = View.INVISIBLE
        Snackbar.make(category_list, exception, Snackbar.LENGTH_LONG)
            .show()
    }


    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }

    private fun onItemClick(categoryId: Int) {
        val bundle = bundleOf("categoryId" to categoryId)
        findNavController().navigate(R.id.action_navigation_home_to_homeAFragment2, bundle)
    }

}

//interface CategoryItemClick {
//    fun itemClick(categoryId: Int)
//}