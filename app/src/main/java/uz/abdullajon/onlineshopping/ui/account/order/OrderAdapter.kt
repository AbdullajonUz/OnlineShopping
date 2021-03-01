package uz.abdullajon.onlineshopping.ui.account.order


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Order

class OrderAdapter(
    private val context: Context,
    private val orderListener: OrderListener
) :
    ListAdapter<Order, OrderAdapter.VH>(ItemDifferOrder()) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.order_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        @SuppressLint("SetTextI18n")
        fun bindData(orderProduct: Order?) {
            if (orderProduct != null) {
                order_date.text = orderProduct.orderDate
                order_id.text = "NO" + orderProduct.orderId
                order_price.text = orderProduct.amount.toString() + " so'm"
                order_state.text = orderProduct.state
                child_product_list.adapter = ChildAdapter(context, orderProduct.orderList!!)
            }
            containerView.setOnClickListener {
                orderListener.itemClick(adapterPosition)
            }
        }
    }

}

class ItemDifferOrder : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean = true

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
        oldItem == newItem
}

interface OrderListener {
    fun itemClick(position: Int)
}

