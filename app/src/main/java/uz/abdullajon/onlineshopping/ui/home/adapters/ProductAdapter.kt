package uz.abdullajon.onlineshopping.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.product_item.*
import uz.abdullajon.onlineshopping.R
import uz.abdullajon.onlineshopping.data.model.Product
import uz.abdullajon.onlineshopping.utils.PATH_URL

class ProductAdapter(
    private val context: Context,
    val onItemClick: (Int) -> Unit
) :
    ListAdapter<Product, ProductAdapter.VH>(ItemDifferProduct()) {

    private val inflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.product_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(product: Product?) {
            if (product != null) {
                Glide
                    .with(context)
                    .load(PATH_URL + product.imageUrl)
                    .into(product_image)

                product_name.text = product.productName
                description.text = product.description

                containerView.setOnClickListener {
                    onItemClick(product.productID)
                }
            }
        }
    }
}

class ItemDifferProduct : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.productID == newItem.productID

}

