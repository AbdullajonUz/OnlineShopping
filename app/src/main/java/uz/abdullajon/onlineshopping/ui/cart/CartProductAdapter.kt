package uz.abdullajon.onlineshopping.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cart_item.*
import kotlinx.android.synthetic.main.custom_add_product.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.utils.PATH_URL

class CartProductAdapter(
    private val context: Context,
    var clickListener: ClickListener
) :
    ListAdapter<CartModel, CartProductAdapter.VH>(ItemDifferCartProduct()) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.cart_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(cartProduct: CartModel?) {
            var quantity: Int
            if (cartProduct != null) {
                Glide
                    .with(context)
                    .load(PATH_URL + cartProduct.productImage)
                    .into(product_image)

                product_name.text = cartProduct.productName
                product_price.text = cartProduct.productPrice.toString()
                value_text.text = cartProduct.quantity.toString()

                plus_btn.setOnClickListener {
                    quantity = value_text.text.toString().toInt() + 1
                    value_text.text = quantity.toString()
                    cartProduct.quantity = value_text.text.toString().toInt()
                    clickListener.quantityClick(cartProduct.quantity!!, cartProduct.id)
                }
                minus_btn.setOnClickListener {
                    if (value_text.text.toString().toInt() != 1) {
                        quantity = value_text.text.toString().toInt() - 1
                        value_text.text = quantity.toString()
                        cartProduct.quantity = value_text.text.toString().toInt()
                        clickListener.quantityClick(cartProduct.quantity!!, cartProduct.id)
                    }
                }
            }
        }
    }

    fun getPrice(): Long {
        var amountPrice = 0L
        currentList.forEach {
            amountPrice += (it.quantity!! * it.productPrice!!)
        }
        return amountPrice
    }
}

class ItemDifferCartProduct : DiffUtil.ItemCallback<CartModel>() {
    override fun areItemsTheSame(oldItem: CartModel, newItem: CartModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CartModel, newItem: CartModel): Boolean =
        oldItem.id == newItem.id
}


interface ClickListener {
    fun quantityClick(quantity: Int, id: Int)
    fun itemClick()
}

