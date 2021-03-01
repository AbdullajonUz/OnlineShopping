package uz.abdullajon.onlineshopping.ui.cart.fullCheckout

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_order_summary.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.*
import uz.abdullajon.onlineshopping.utils.PREF_NAME
import uz.abdullajon.onlineshopping.utils.SuccessDialog
import java.text.SimpleDateFormat
import java.util.*

class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary),
    OrderSummaryContract.CartView, View.OnClickListener {

    private var card: CustomerCard? = null
    private lateinit var adapter: OrderProductAdapter
    private lateinit var presenter: OrderSummaryPresenterImpl
    private var dialog: AlertDialog? = null
    private lateinit var payment: Payment
    private val orderList = mutableListOf<OrderDetail>()
    private var address: String? = ""


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = OrderSummaryPresenterImpl(this, requireContext())
        presenter.getCartProduct()
        adapter = OrderProductAdapter(requireContext())
        products_order_list.adapter = adapter
        val preference = requireContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        address = preference.getString("address", "")
        tv_order_address.text = address

        val paymentId = requireArguments().getInt("paymentId", 0)
        if (paymentId == 1) {
            card = requireArguments().getParcelable("card")
        }
        if (card != null) {
            payment =
                Payment("card", false, card!!.cardNumber.toString(), card!!.cardName.toString())
            tv_order_payment_name.text = card!!.cardName
            tv_order_payment_number.text = "...${card!!.cardNumber!!.substring(14)}"
        } else {
            payment = Payment("cash", false, "", "")
            tv_order_payment_name.text = "pay in cash"
        }
        back_btn.setOnClickListener {
            findNavController().popBackStack()
        }
        pay_btn.setOnClickListener(this)


    }

    override fun getResponse(list: List<CartModel>) {
        list.forEach {
            orderList.add(OrderDetail(it.id.toLong(), it.productName, it.productPrice, it.quantity!!.toLong()))
        }
        adapter.submitList(list)
        getPrice()
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
//        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    override fun onFail(exception: String) {
        Toast.makeText(
            requireContext(),
            "Order not implemented, please try again",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun successAuth() {

        val successDialog = SuccessDialog(requireContext())
        successDialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun getPrice(): Long {
        val totalPrice = adapter.getPrice() + 15000L
        subtotal.text = "subtotal........................${adapter.getPrice()} so'm"
        total.text = "total........................${totalPrice} so'm"
        return totalPrice
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {
        if (orderList.isNotEmpty()) {
            val auth = FirebaseAuth.getInstance()
            val userId = auth.uid

            val date = Date()
            var df = SimpleDateFormat("yyyy-MM-dd, HH:mm")
            val c1 = Calendar.getInstance()
            val currentDate = df.format(date)

            c1.add(Calendar.DAY_OF_YEAR, 10)
            df = SimpleDateFormat("yyyy-MM-dd, HH:mm")
            val resultDate = c1.time
            val dueDate = df.format(resultDate)

            presenter.setOrder(
                Order(
                    userId.toString(),
                    getPrice(),
                    "Processed",
                    currentDate,
                    dueDate,
                    payment,
                    orderList,
                    address
                )
            )
        }
    }
}