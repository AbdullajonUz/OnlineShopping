package uz.abdullajon.onlineshopping.ui.cart.fullCheckout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_product_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.CartModel
import uz.abdullajon.onlineshopping.ui.cart.ItemDifferCartProduct
import uz.abdullajon.onlineshopping.utils.PATH_URL

class OrderProductAdapter(
    private val context: Context
) :
    ListAdapter<CartModel, OrderProductAdapter.VH>(ItemDifferCartProduct()) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.order_product_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(cartProduct: CartModel?) {
            if (cartProduct != null) {
                Glide
                    .with(context)
                    .load(PATH_URL + cartProduct.productImage)
                    .into(product_image)
                product_name.text = cartProduct.productName
                product_price.text = cartProduct.productPrice.toString()
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
