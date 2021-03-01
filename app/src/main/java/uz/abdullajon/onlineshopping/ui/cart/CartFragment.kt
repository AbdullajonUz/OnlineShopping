package uz.abdullajon.onlineshopping.ui.cart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cart.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.ui.base.BaseFragment
import uz.abdullajon.onlineshopping.utils.SwipeToDeleteCallback


class CartFragment : BaseFragment(), CartContract.CartView, ClickListener, View.OnClickListener {

    override fun layoutRes(): Int = R.layout.fragment_cart
    private lateinit var presenter: CartPresenterImpl
    private lateinit var adapter: CartProductAdapter
    private var cartList = mutableListOf<CartModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartProductAdapter(requireContext(), this)
        cart_product_list.adapter = adapter
        presenter = CartPresenterImpl(this, requireContext())
        presenter.getCartProduct()
        enableSwipeToDeleteAndUndo()
        checkout_btn.setOnClickListener(this)
    }

    override fun getResponse(list: List<CartModel>) {
        cartList.clear()
        cartList.addAll(list)
        adapter.submitList(cartList)
        getPrice()
    }

    private fun getPrice() {
        products_price.text = adapter.getPrice().toString()
    }

    override fun quantityClick(quantity: Int, id: Int) {
        presenter.quantityClick(quantity, id)
        getPrice()
    }

    override fun itemClick() {

    }

    override fun onClick(v: View?) {
        findNavController().navigate(R.id.action_navigation_cart_to_shippingFragment)
//        val dialog = SuccessDialog(requireContext())
//        dialog.show()
    }


    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item = cartList[position]
                    cartList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    getPrice()

                    val snackbar = Snackbar
                        .make(
                            cart_product_list,
                            "Item was removed from the list.",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.setAction("UNDO") {
                        cartList.add(position, item)
                        adapter.notifyItemInserted(position)
                        cart_product_list.scrollToPosition(position)
                        getPrice()
                    }
                        .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                if (event == DISMISS_EVENT_TIMEOUT) {
                                    presenter.deleteById(item.id.toLong())
                                }
                            }
                        })
                        .setActionTextColor(Color.YELLOW)
                        .show()
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(cart_product_list)
    }

    override fun onStop() {
        super.onStop()
        presenter.onCancel()
    }
}