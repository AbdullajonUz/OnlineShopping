package uz.abdullajon.onlineshopping.ui.account.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_order_history.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Order

class OrderHistoryFragment : Fragment(R.layout.fragment_order_history), OrderContract.OrderView,
    OrderListener {

    private lateinit var adapter: OrderAdapter
    private lateinit var presenter: OrderContractPresenterImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = OrderContractPresenterImpl(this)
        presenter.loadData()
        adapter = OrderAdapter(requireContext(), this)
        order_list.layoutManager = LinearLayoutManager(requireContext())
        order_list.adapter = adapter
    }

    override fun getResponse(order: List<Order>) {
        progress_bar.visibility = View.INVISIBLE
        order_list.visibility = View.VISIBLE
        adapter.submitList(order)
    }

    override fun onFail(exception: String) {
        progress_bar.visibility = View.INVISIBLE
        order_list.visibility = View.VISIBLE
    }

    override fun itemClick(position: Int) {

    }

}
