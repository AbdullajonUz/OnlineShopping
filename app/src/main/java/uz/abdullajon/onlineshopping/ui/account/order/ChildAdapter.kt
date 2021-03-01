package uz.abdullajon.onlineshopping.ui.account.order

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.child_rv_item_product.view.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.OrderDetail

class ChildAdapter(private val context: Context, private val children: List<OrderDetail>) :
    RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.child_rv_item_product, parent, false))
    }

    override fun getItemCount(): Int {
        return children.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val child = children[position]
        if (!child.productName.isNullOrEmpty() && child.productName.length > 15) {
            holder.name.text = child.productName.substring(0, 15)
        } else {
            holder.name.text = child.productName
        }
        holder.quantity.text = child.quantity.toString() + "X"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.product_name
        val quantity: TextView = itemView.product_quantity

    }
}